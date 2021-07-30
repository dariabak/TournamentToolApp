package bracket

import BottomSheet.BottomSheetFragment
import java.io.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import helpers.BracketHelper
import helpers.BracketLineView
import androidx.databinding.DataBindingUtil
import com.example.tournamenttool.databinding.FragmentBracketsLayoutBinding
import java.util.*
import androidx.lifecycle.Observer
import java.math.BigDecimal
import java.math.BigInteger
import bracket.*
import bracket.model.BracketPosition
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

interface BracketsFragmentInterface {
    fun updateBracket(viewModel: BracketViewModel, index: Int)
    fun displayBracket(topText: String, bottomText: String, handler: (Winner) -> Unit)
}

class BracketsFragment : Fragment(), BracketsFragmentInterface {

    private lateinit var layout: ViewGroup
    private lateinit var binding: FragmentBracketsLayoutBinding
    private var bracketsArray = ArrayList<BracketView>()
    private var nextBracketId = 0
    private lateinit var interactor: BracketInteractorInterface

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_brackets_layout,
            container,
            false
        )
        layout = binding.bracketsLayout as ViewGroup
        val presenter = BracketPresenter(this)
        interactor = BracketInteractor(presenter)
        val args = BracketsFragmentArgs.fromBundle(requireArguments())
        val numberOfTeams = args.numberOfTeams
        val positionArray = BracketHelper.getBracketPosition(numberOfTeams)
        var teamNamesArray = args.teamNamesArray.toCollection(ArrayList())
        var teamNames: ArrayList<String> = ArrayList(teamNamesArray)
        binding.tournamentName.text = args.tournamentName
        createBrackets(numberOfTeams, positionArray)
        interactor.setUpBrackets(teamNames, positionArray)

        binding.saveButton.setOnClickListener {
            interactor.saveTournament(args.tournamentName, requireActivity())
        }
        return binding.root
    }

    private fun createBrackets(numberOfTeams: Int, array: ArrayList<BracketPosition>) {
        var bracketLinesArray = ArrayList<BracketLineView>()
        for(element in array) {
           val bracket = BracketView(requireActivity(), element)
           bracketsArray.add(bracket)
           bracket.setId(bracketsArray.size - 1)
           layout.addView(bracket)
           bracket.addClickHandler {
               interactor.showBracketBottomSheet(bracket.bracketPosition)
           }
       }
        for(i in 1 until array.size) {
            var line = BracketLineView(requireActivity())
            bracketLinesArray.add(line)
            layout.addView(line)
        }
        BracketHelper.positionBrackets(bracketsArray)
        BracketHelper.positionBracketLines(bracketLinesArray, bracketsArray)
    }

    override fun updateBracket(viewModel: BracketViewModel, index: Int) {
        bracketsArray.get(index).update(viewModel)
    }
    override fun displayBracket(topText: String, bottomText: String, handler: (Winner) -> Unit) {
        val bottomFragment = BottomSheetFragment(topText, bottomText)
        bottomFragment.update(handler)
        bottomFragment.show(getFragmentManager()!!, "123")
    }
}