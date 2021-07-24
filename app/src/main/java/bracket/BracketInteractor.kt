package bracket

import android.util.Log
import bracket.model.BracketPosition
import kotlin.math.floor
import kotlin.math.pow
import java.math.BigDecimal


interface BracketInteractorInterface{
 fun setUpBrackets(teamNames: ArrayList<String>, positionArrayList: ArrayList<BracketPosition>)
 fun showBracketBottomSheet(position: BracketPosition)

}

class BracketInteractor(val presenter: BracketPresenterInterface): BracketInteractorInterface {
    var bracketsArrayList = ArrayList<Bracket>()

    override fun setUpBrackets(teamNames: ArrayList<String>, positionArrayList: ArrayList<BracketPosition>) {
        for(element in positionArrayList) {
            var bracket = Bracket(element)
            bracketsArrayList.add(bracket)
        }
        setTeamsNames(teamNames, bracketsArrayList)
        for(bracket in bracketsArrayList) {
            if(bracket.team1 != "" || bracket.team2 != "") {
                var index = findBracketIndex(bracket.bracketPosition)
                presenter.updateBracket(bracket, index)
            }
        }
    }
    private fun setTeamsNames(teamNames: ArrayList<String>, brackets: ArrayList<Bracket>) {
        var index = ArrayList<Int>()
        teamNames.shuffle()
        var bracketsInFirstColumn = (brackets.size + 1)/2
        for(i in bracketsInFirstColumn - 1 downTo 0) {
            brackets.get(i).team1 = teamNames.get(i)
            teamNames.removeAt(i)
            index.add(i)
        }
        index.shuffle()
        for(team in teamNames){
            brackets.get(index.get(0)).team2 = team
            index.remove(index.get(0))
        }
    }
    override fun showBracketBottomSheet(position: BracketPosition) {
        val index = findBracketIndex(position)
        val bracket = bracketsArrayList.get(index)

        presenter.presentBracket(bracket, { winner ->
            updateWinner(bracket, winner)
        })
    }

    fun updateWinner(bracket: Bracket, winner: Winner) {
        bracket.winner = winner
        val currentBracketIndex = findBracketIndex(bracket.bracketPosition)
        presenter.updateBracket(bracket, currentBracketIndex)
        if(!isLastBracket(currentBracketIndex)){
            val nextBracketIndex = getNextBracketIndex(currentBracketIndex)
                val a = BigDecimal("$currentBracketIndex")
                val b = BigDecimal("2")
                if (a.rem(b).toInt() == 0) {
                    if(winner == Winner.TOP) {
                        bracketsArrayList.get(nextBracketIndex).team1 = bracket.team1
                    } else {
                        bracketsArrayList.get(nextBracketIndex).team1 = bracket.team2
                    }
                } else {
                    if(winner == Winner.BOTTOM) {
                        bracketsArrayList.get(nextBracketIndex).team2 = bracket.team2
                    } else {
                        bracketsArrayList.get(nextBracketIndex).team2 = bracket.team1
                    }
                }
            presenter.updateBracket(bracketsArrayList.get(nextBracketIndex), nextBracketIndex)
        }
    }

    fun findBracketIndex(position: BracketPosition): Int {
        var previousColumnsRowCounter = 0.0
        for(i in 0 until position.col){
            previousColumnsRowCounter += (bracketsArrayList.size + 1) / 2.0.pow(i)/2
        }
        return previousColumnsRowCounter.toInt() + position.row
    }

    fun getNextBracketIndex(index: Int): Int {
        var firstColumn = (bracketsArrayList.size + 1)/2
        var nextBracketId = floor(index.toDouble()/2.0) + firstColumn
        return nextBracketId.toInt()
    }
    fun isLastBracket(index: Int): Boolean {
        return bracketsArrayList.size - 1 == index
    }
}