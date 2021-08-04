package bracket

import BottomSheet.BottomSheetFragment
import java.io.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import helpers.BracketHelper
import helpers.BracketLineView
import androidx.databinding.DataBindingUtil
import com.example.tournamenttool.databinding.FragmentBracketsLayoutBinding
import java.util.*
import bracket.*
import bracket.model.BracketPosition

interface BracketsFragmentInterface {
    fun updateBracket(viewModel: BracketViewModel, index: Int)
    fun displayBracket(topText: String, bottomText: String, handler: (Winner) -> Unit)
    fun createBrackets(bracketsPositions: ArrayList<BracketPosition>)
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
        val service: BracketServiceInterface = BracketService(requireActivity())
        val repo: BracketRepoInterface = BracketRepo(service)
        val presenter = BracketPresenter(this)
        interactor = BracketInteractor(presenter, repo)

        val args = BracketsFragmentArgs.fromBundle(requireArguments())
        val numberOfTeams = args.numberOfTeams
        val positionArray = BracketHelper.getBracketPosition(numberOfTeams)
        var teamNamesArray = args.teamNamesArray?.toCollection(ArrayList())
        binding.tournamentName.text = args.tournamentName
        if(teamNamesArray != null) {
            var teamNames: ArrayList<String> = ArrayList(teamNamesArray)
            createBrackets(positionArray)
            interactor.setUpBrackets(teamNames, positionArray)
        } else {
            interactor.getTournament(args.tournamentName)
        }

        binding.saveButton.setOnClickListener {
            interactor.saveTournament(args.tournamentName)
        }
        return binding.root
    }

    override fun createBrackets(bracketsPositions: ArrayList<BracketPosition>) {
        var bracketLinesArray = ArrayList<BracketLineView>()
        for(element in bracketsPositions) {
           val bracket = BracketView(requireActivity(), element)
           bracketsArray.add(bracket)
           bracket.setId(bracketsArray.size - 1)
           layout.addView(bracket)
           bracket.addClickHandler {
               interactor.showBracketBottomSheet(bracket.bracketPosition)
           }
       }
        for(i in 1 until bracketsPositions.size) {
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