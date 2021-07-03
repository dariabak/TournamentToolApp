package bracket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import helpers.BracketHelper
import kotlinx.android.synthetic.main.bracket_layout.view.*




class BracketsFragment : Fragment() {

    private lateinit var layout: ViewGroup


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_brackets_layout, container, false)
        layout = rootView.findViewById<View>(R.id.brackets_layout) as ViewGroup

        val args = BracketsFragmentArgs.fromBundle(requireArguments())
        val numberOfTeams = args.numberOfTeams
        createBrackets("111111", numberOfTeams)

        return rootView
    }

    private fun createBrackets(text: String, numberOfTeams: Int) {
        val array = BracketHelper.getBracketPosition(numberOfTeams)
        var bracketsArray = ArrayList<BracketsView>()

       for(element in array) {
           val bracket = BracketsView(requireActivity(), element)
           bracketsArray.add(bracket)
           bracket.setId(bracketsArray.size)
           layout.addView(bracket)
       }
        BracketHelper.positionBrackets(bracketsArray)
//        var lp = array.get(1).getLayoutParams() as  ViewGroup.MarginLayoutParams
//        var viewX = lp.topMargin

    }

}