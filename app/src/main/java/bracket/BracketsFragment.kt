package bracket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import kotlinx.android.synthetic.main.bracket_layout.view.*
import kotlin.math.floor
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
        var fullMatches = 0
        var log = log(numberOfTeams.toDouble(), 2.toDouble())
        var x = floor(log)
        var two = 2.toDouble()
        var pow = two.pow(x.toDouble()).toInt()

        fullMatches = if(pow == numberOfTeams){
            numberOfTeams/2
        } else {
            numberOfTeams - two.pow(x.toDouble()).toInt()
        }
        var j = 1

        for(i in 1..fullMatches){
            val bracket = BracketsView(requireActivity())

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0,0 + 200*j , 0, 0)
            bracket.layoutParams = params
            bracket.team1.text = text
            bracket.team2.text = text

            layout.addView(bracket)
            j+=1
        }
        var emptyMatches = numberOfTeams- 2*fullMatches
        for(i in 1..emptyMatches){
            val bracket = BracketsView(requireActivity())

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(0,0 + 200*j , 0, 0)
            bracket.layoutParams = params
            bracket.team1.text = text
            bracket.team2.text = ""

            layout.addView(bracket)
            j+=1
        }
        var k = 1
        var secondStage = (fullMatches + emptyMatches)/2

        for(i in 1..secondStage){
            val bracket = BracketsView(requireActivity())

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(200,0 + 300*k , 0, 0)
            bracket.layoutParams = params
            bracket.team1.text = text
            bracket.team2.text = ""
            k+=1.5
            layout.addView(bracket)
        }

    }
}