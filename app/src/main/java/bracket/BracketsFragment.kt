package bracket

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.tournamenttool.R
import kotlinx.android.synthetic.main.bracket_layout.view.*

class BracketsFragment : Fragment(R.layout.fragment_brackets_layout) {

    private lateinit var layout: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout = R.id.brackets_layout as ViewGroup


        createBrackets("111111", 0)
        createBrackets("222222", 1)

        createBrackets("333333", 2)
    }
    private fun createBrackets(text: String, i: Int) {
        val bracket = BracketsView(this)

        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0,0 + 200*i , 0, 0)
        bracket.layoutParams = params
        bracket.team1.text = text
        bracket.team2.text = text
        layout.addView(bracket)

    }
}