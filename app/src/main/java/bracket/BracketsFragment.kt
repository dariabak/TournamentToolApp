package bracket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import helpers.BracketHelper
import helpers.BracketLineView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tournamenttool.databinding.FragmentBracketsLayoutBinding
import java.util.*
import androidx.lifecycle.Observer

class BracketsFragment : Fragment() {

    private lateinit var layout: ViewGroup
    private lateinit var binding: FragmentBracketsLayoutBinding
    private lateinit var viewModel: BracketsViewModel
    private var bracketsArray = ArrayList<BracketView>()
    private var nextBracketId = 0

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_brackets_layout,
            container,
            false
        )

        layout = binding.bracketsLayout as ViewGroup

        val args = BracketsFragmentArgs.fromBundle(requireArguments())
        val numberOfTeams = args.numberOfTeams
        createBrackets(numberOfTeams)




        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BracketsViewModel::class.java)
        viewModel.bracketId.observe(requireActivity(), Observer { id ->
            nextBracketId =  BracketHelper.findNextBracket(id, bracketsArray)
        })

        viewModel.winner.observe(requireActivity(), Observer { winner->
            bracketsArray.get(nextBracketId).setTeam1(winner)
        })
    }
    private fun createBrackets(numberOfTeams: Int) {
        val array = BracketHelper.getBracketPosition(numberOfTeams)
        var bracketLinesArray = ArrayList<BracketLineView>()

       for(element in array) {
           val bracket = BracketView(requireActivity(), element)
           bracketsArray.add(bracket)
           bracket.setId(bracketsArray.size - 1)
           layout.addView(bracket)
       }
        for(i in 1 until array.size) {
            var line = BracketLineView(requireActivity())
            bracketLinesArray.add(line)
            layout.addView(line)
        }

        BracketHelper.setTeamsNames(numberOfTeams, bracketsArray)
        BracketHelper.positionBrackets(bracketsArray)
        BracketHelper.positionBracketLines(bracketLinesArray, bracketsArray)
    }

}