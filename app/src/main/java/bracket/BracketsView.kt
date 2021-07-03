package bracket

import android.content.Context
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import bracket.model.BracketPosition
import com.example.tournamenttool.R
import kotlinx.android.synthetic.main.bracket_layout.view.*


class BracketsView(context: Context, val bracketPosition: BracketPosition): ConstraintLayout(context) {

    init {
       inflate(context, R.layout.bracket_layout, this)
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
     fun setLayoutParams(leftMargin: Int, topMargin: Int) {
         val params = FrameLayout.LayoutParams(
             FrameLayout.LayoutParams.WRAP_CONTENT,
             FrameLayout.LayoutParams.WRAP_CONTENT
         )

         params.setMargins(leftMargin, topMargin, 0, 0)
         this.layoutParams = params
         this.team1.text = this.getMidY().toString()
         this.team2.text = "22222"
     }
}