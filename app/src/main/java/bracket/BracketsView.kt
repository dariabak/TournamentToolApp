package bracket

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tournamenttool.R
import kotlinx.android.synthetic.main.bracket_layout.view.*


class BracketsView(context: Context, leftMargin: Int, topMargin: Int): ConstraintLayout(context) {

    init {
       inflate(context, R.layout.bracket_layout, this)
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        params.setMargins(leftMargin,0 + topMargin, 0, 0)
        this.layoutParams = params
        this.team1.text = this.getMidY().toString()
        this.team2.text = "22222"
   }

    fun getMidY(): Int {
        measure(
            // horizontal space requirements as imposed by the parent
            0, // widthMeasureSpec
            // vertical space requirements as imposed by the parent
            0 // heightMeasureSpec
        )
        // return the raw measured height of this view
        return measuredHeight / 2
    }

}