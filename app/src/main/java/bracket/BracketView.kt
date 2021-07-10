package bracket

import android.content.Context
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import bracket.model.BracketPosition
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.BracketLayoutBinding
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import androidx.navigation.findNavController


class BracketView(context: Context, val bracketPosition: BracketPosition): ConstraintLayout(context) {
    private var binding: BracketLayoutBinding

    init {
        inflate(context, R.layout.bracket_layout, this)
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.bracket_layout,this , true)
        binding.bracket.setOnClickListener {
            clickListener()
        }
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

    fun getViewWidth(): Int {
        measure(
            // horizontal space requirements as imposed by the parent
            0, // widthMeasureSpec
            // vertical space requirements as imposed by the parent
            0 // heightMeasureSpec
        )
        // return the raw measured height of this view
        return measuredWidth
    }

     fun setLayoutParams(leftMargin: Int, topMargin: Int) {
         val params = FrameLayout.LayoutParams(
             FrameLayout.LayoutParams.WRAP_CONTENT,
             FrameLayout.LayoutParams.WRAP_CONTENT
         )

         params.setMargins(leftMargin, topMargin, 0, 0)
         this.layoutParams = params
     }

    fun setTeam1(team: String) {
        binding.team1.text = team
    }

    fun setTeam2(team: String) {
        binding.team2.text = team
    }

    fun clickListener() {
        this.findNavController().navigate(BracketsFragmentDirections.actionBracketsFragmentToBottomSheet())
        binding.team1.text = "1111111111"
        binding.team2.text = "22222222"
    }
}