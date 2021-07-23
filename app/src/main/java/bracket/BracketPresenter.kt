package bracket

import bracket.model.BracketPosition
import com.example.tournamenttool.R
import kotlin.math.pow

interface BracketPresenterInterface {
 fun updateBracket(bracket: Bracket, index: Int)
}

class BracketPresenter(val fragment: BracketsFragmentInterface,val size: Int): BracketPresenterInterface {
    override fun updateBracket(bracket: Bracket, index: Int) {
        var topColor = R.color.black
        var bottomColor = R.color.black

        if(bracket.winner == Winner.TOP){
            topColor = R.color.green
            bottomColor = R.color.red
        } else if(bracket.winner == Winner.BOTTOM) {
            topColor = R.color.red
            bottomColor = R.color.green
        }

        val viewModel = BracketViewModel(bracket.team1, bracket.team2, topColor, bottomColor)
        fragment.updateBracket(viewModel, index)
    }

}