package bracket

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tournamenttool.R


class BracketsView(context: Context): ConstraintLayout(context) {

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

        val midY = measuredHeight/2
        // the raw measured height of this view
        return midY
    }
    fun getLocation():IntArray {
        val location: IntArray = IntArray(2)
        this.getLocationOnScreen(location)
        return location
    }

}