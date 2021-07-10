package bracket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import helpers.BracketHelper
import helpers.BracketLineView
import android.widget.Toast

class BracketsFragment : Fragment() {

    private lateinit var layout: ViewGroup


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_brackets_layout, container, false)
        layout = rootView.findViewById<View>(R.id.brackets_layout) as ViewGroup

        val args = BracketsFragmentArgs.fromBundle(requireArguments())
        val numberOfTeams = args.numberOfTeams
        createBrackets(numberOfTeams)

        return rootView
    }

    private fun createBrackets(numberOfTeams: Int) {
        val array = BracketHelper.getBracketPosition(numberOfTeams)
        var bracketsArray = ArrayList<BracketView>()
        var bracketLinesArray = ArrayList<BracketLineView>()

       for(element in array) {
           val bracket = BracketView(requireActivity(), element)
           bracketsArray.add(bracket)
           bracket.setId(bracketsArray.size)
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

    fun clicked() {

    }

}