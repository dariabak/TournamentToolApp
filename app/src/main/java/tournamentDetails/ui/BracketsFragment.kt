package tournamentDetails.ui

import BottomSheet.BottomSheetFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import bracket.*
import tournamentDetails.business.BracketPosition
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.FragmentBracketsLayoutBinding
import helpers.BracketHelper
import helpers.BracketLineView
import tournamentDetails.data.BracketRepo
import tournamentDetails.data.BracketRepoInterface
import java.util.*


interface BracketsFragmentInterface {
    fun updateBracket(viewModel: BracketViewModel, index: Int)
    fun displayBracket(topText: String, bottomText: String, handler: (Winner) -> Unit)
    fun createBrackets(bracketsPositions: ArrayList<BracketPosition>)
    fun updateTournamentName(name: String)
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
        if(teamNamesArray != null) {
            var teamNames: ArrayList<String> = ArrayList(teamNamesArray)
            createBrackets(positionArray)
            (activity as? AppCompatActivity)?.supportActionBar?.title = args.tournamentName
            interactor.setUpBrackets(teamNames, positionArray)
        } else {
            interactor.getTournament(args.tournamentId)
        }

        binding.saveButton.setOnClickListener {
            interactor.saveTournament(args.tournamentName, args.tournamentId)
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
        bottomFragment.show(requireFragmentManager(), "123")
    }

    override fun updateTournamentName(name: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = name
    }
}