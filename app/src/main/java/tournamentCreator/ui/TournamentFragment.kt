package tournamentCreator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.tournamenttool.R
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.inputmethod.EditorInfo
import com.example.tournamenttool.databinding.FragmentTournamentLayoutBinding
import tournamentCreator.business.TournamentViewModel
import java.util.*


class TournamentFragment: Fragment() {
    private lateinit var binding: FragmentTournamentLayoutBinding
    private lateinit var teamNamesLayout: ViewGroup
    private lateinit var viewModel: TournamentViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tournament_layout,
            container,
            false
        )

        teamNamesLayout = binding.teamNamesLayout as ViewGroup
        viewModel = ViewModelProviders.of(this).get(TournamentViewModel::class.java)
        binding.tournamentViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter: ArrayAdapter<Int> = ArrayAdapter<Int>(requireActivity(), android.R.layout.simple_spinner_item, viewModel.numberOfTeamsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.numberOfTeamsSpinner.adapter = adapter

        val typeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, viewModel.typesOfScheduleArray)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeOfScheduleSpinner.adapter = typeAdapter

        binding.submitButton.setOnClickListener{
            teamNamesLayout.removeAllViews()
            var numberOfTeams =  binding.numberOfTeamsSpinner.selectedItem as Int
            val action =
                TournamentFragmentDirections.actionTournamentToBrackets()
            action.numberOfTeams = numberOfTeams
            action.teamNamesArray = viewModel.getArrayOfNames()
            action.tournamentName = binding.nameOfTournament.text.toString()
            action.tournamentId = UUID.randomUUID().toString()

            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.nameOfTournament.doAfterTextChanged {
            viewModel.tournamentName = it.toString()
            viewModel.checkTournamentNameLength()
        }

        viewModel.canContinue.observe(this, Observer {
            if(it) {
                binding.submitButton.isEnabled = true
                binding.submitButton.alpha = 1.0f

            } else {
                binding.submitButton.isEnabled = false
                binding.submitButton.alpha = 0.4f

            }
        })

        viewModel.chosenNumber.observe(this, Observer {
            if(viewModel.switchOn.value == true) {
                teamNamesLayout.removeAllViews()
                for (i in 0 until it) {
                    var editText = EditText(context)
                    editText.setSingleLine()
                    editText.setMaxLength(10)
                    editText.imeOptions = EditorInfo.IME_ACTION_NEXT

                    if (viewModel.teamsNames.containsKey(i)) {
                        editText.setText(viewModel.teamsNames[i])
                        teamNamesLayout.addView(editText)
                        editText.doAfterTextChanged {
                            viewModel.teamsNames[i] = it.toString()
                        }
                    }
                    else {
                        editText.doAfterTextChanged {
                            viewModel.teamsNames[i] = it.toString()
                        }
                        var num = i + 1
                        editText.hint = "Team $num"
                        teamNamesLayout.addView(editText)
                    }
                }
            }
        })

        binding.numberOfTeamsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                teamNamesLayout.removeAllViews()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val number = binding.numberOfTeamsSpinner.selectedItem as Int
                viewModel.numberOfTeams = number
                viewModel.setChosenNumber(number)
            }

        }

        binding.enterTeamNamesSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.switchOn()
                val number = viewModel.chosenNumber.value as Int
                    for (i in 0 until number) {
                        var editText = EditText(context)
                        editText.setMaxLength(10)
                        editText.setSingleLine()
                        editText.imeOptions = EditorInfo.IME_ACTION_NEXT
                        if (viewModel.teamsNames.containsKey(i)) {
                            editText.setText(viewModel.teamsNames[i])
                            teamNamesLayout.addView(editText)
                            editText.doAfterTextChanged {
                                viewModel.teamsNames[i] = it.toString()
                            }
                        }
                        else {
                            editText.doAfterTextChanged {
                                viewModel.teamsNames[i] = it.toString()
                            }
                            var num = i + 1
                            editText.hint = "Team $num"
                            teamNamesLayout.addView(editText)
                        }
                    }
            } else {
                viewModel.switchOff()
                teamNamesLayout.removeAllViews()
            }
        }

        (activity as? androidx.appcompat.app.AppCompatActivity)?.supportActionBar?.title = "Tournament Tool"
        return binding.root
    }
    private fun EditText.setMaxLength(maxLength: Int){
        filters = arrayOf<InputFilter>(LengthFilter(maxLength))
    }
}