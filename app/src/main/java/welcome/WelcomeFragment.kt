package welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.FragmentWelcomeLayoutBinding


class WelcomeFragment: Fragment() {
    private lateinit var binding: FragmentWelcomeLayoutBinding
    private lateinit var teamNamesLayout: ViewGroup
    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_welcome_layout,
            container,
            false
        )
        teamNamesLayout = binding.teamNamesLayout as ViewGroup
        viewModel = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)
        binding.welcomeViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter: ArrayAdapter<Int> = ArrayAdapter<Int>(requireActivity(), android.R.layout.simple_spinner_item, viewModel.numberOfTeamsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.numberOfTeamsSpinner.adapter = adapter

        val typeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, viewModel.typesOfScheduleArray)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeOfScheduleSpinner.adapter = typeAdapter

        binding.submitButton.setOnClickListener{
          var numberOfTeams =  binding.numberOfTeamsSpinner.selectedItem as Int
            val action = WelcomeFragmentDirections.actionWelcomeToBrackets()
            action.numberOfTeams = numberOfTeams
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.enterTeamNamesSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                var number = binding.numberOfTeamsSpinner.selectedItem as Int
                    for (i in 0 until number) {
                        if (i < viewModel.teamsNames.size) {
                            teamNamesLayout.addView(viewModel.teamsNames.get(i))
                        }
                        else {
                            var editText = EditText(context)
                            viewModel.teamsNames.add(editText)
                            teamNamesLayout.addView(editText)
                        }
                    }
            } else {
                teamNamesLayout.removeAllViews()
            }
        }

        return binding.root
    }
}