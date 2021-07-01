package bracket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import helpers.BracketHelper
import kotlinx.android.synthetic.main.bracket_layout.view.*
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow


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
        var j = 0
        val previousBracketPosition = BracketHelper.getPreviousBrackets(2, 3)

//        for(i in 1..numberOfBrackets){
//            val bracket = BracketsView(requireActivity(), 0, 200*j)
//            array.add(bracket)
//            bracket.setId(array.size)
//            layout.addView(bracket)
//            j+=1
//        }

//        var lp = array.get(1).getLayoutParams() as  ViewGroup.MarginLayoutParams
//        var viewX = lp.topMargin

//        var k = 1
//        var secondStage = numberOfBrackets/2
//
//        for(i in 1..secondStage){
//            val bracket = BracketsView(requireActivity())
//
//            val params = ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT
//            )
//
//            params.setMargins(200, 0 + 300*k , 0, 0)
//            bracket.layoutParams = params
//            bracket.team1.text = text
//            bracket.team2.text = "222222"
//            k+=2
//            layout.addView(bracket)
//        }

    }

}