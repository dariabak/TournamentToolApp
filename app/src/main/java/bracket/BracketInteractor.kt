package bracket

import android.content.Context
import bracket.model.BracketPosition
import kotlin.math.floor
import kotlin.math.pow
import java.math.BigDecimal
import bracket.BracketRepoInterface


interface BracketInteractorInterface{
 fun setUpBrackets(teamNames: ArrayList<String>, positionArrayList: ArrayList<BracketPosition>)
 fun showBracketBottomSheet(position: BracketPosition)
 fun saveTournament(tournamentName: String, numberOfTeams: Int, tournamentId: String)
 fun getTournament(tournamentId: String)
}

class BracketInteractor(val presenter: BracketPresenterInterface, val repo: BracketRepoInterface): BracketInteractorInterface {
    var bracketsArrayList = ArrayList<Bracket>()

    override fun setUpBrackets(teamNames: ArrayList<String>, positionArrayList: ArrayList<BracketPosition>) {
        for(element in positionArrayList) {
            var bracket = Bracket(element)
            bracketsArrayList.add(bracket)
        }
        setTeamsNames(teamNames, bracketsArrayList)
        for(bracket in bracketsArrayList) {
            var index = findBracketIndex(bracket.bracketPosition)
            presenter.updateBracket(bracket, index)
            if(bracket.bracketPosition.col == 0) {
                if (bracket.team2 == "") {
                    updateWinner(bracketsArrayList.get(index), Winner.TOP)
                } else if (bracket.team1 == "") {
                    updateWinner(bracketsArrayList.get(index), Winner.BOTTOM)
                }
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
        bracketsArrayList.get(currentBracketIndex).winner = winner
        presenter.updateBracket(bracket, currentBracketIndex)
        if(!isLastBracket(currentBracketIndex)){
            val nextBracketIndex = getNextBracketIndex(currentBracketIndex)
                if (isTop(currentBracketIndex)) {
                    if(bracket.winner == Winner.TOP) {
                        bracketsArrayList.get(nextBracketIndex).team1 = bracket.team1
                    } else {
                        bracketsArrayList.get(nextBracketIndex).team1 = bracket.team2
                    }
                } else {
                    if(bracket.winner == Winner.BOTTOM) {
                        bracketsArrayList.get(nextBracketIndex).team2 = bracket.team2
                    } else {
                        bracketsArrayList.get(nextBracketIndex).team2 = bracket.team1
                    }
                }
            presenter.updateBracket(bracketsArrayList.get(nextBracketIndex), nextBracketIndex)
            var isDone = false
            var nextIndex = nextBracketIndex
            var previousIndex = nextIndex
            var isFirst = true
            while(!isDone) {
                if(isFirst) {
                    bracketsArrayList.get(nextIndex).winner = Winner.NONE
                    presenter.updateBracket(
                        bracketsArrayList.get(nextIndex),
                        nextIndex
                        )
                } else {
                   bracketsArrayList.get(nextIndex).winner = Winner.NONE
                    if(isTop(previousIndex)) {
                        bracketsArrayList.get(nextIndex).team1 = ""
                    } else {
                        bracketsArrayList.get(nextIndex).team2 = ""
                    }
                    presenter.updateBracket(
                        bracketsArrayList.get(nextIndex),
                        nextIndex
                    )
                }
                if(isLastBracket(nextIndex)){
                    isDone = true
                }
                previousIndex = nextIndex
                nextIndex = getNextBracketIndex(nextIndex)
                isFirst = false
            }
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
    fun isTop(index: Int): Boolean {
        val a = BigDecimal("$index")
        val b = BigDecimal("2")
        return (a.rem(b).toInt() == 0)
    }

    override fun saveTournament(tournamentName: String, numberOfTeams: Int, tournamentId: String) {
        repo.saveTournament(tournamentName, bracketsArrayList, numberOfTeams, tournamentId)
    }

    override fun getTournament(tournamentId: String){
        bracketsArrayList = repo.getTournament(tournamentId)
        var positionArrayList = ArrayList<BracketPosition>()
        for(bracket in bracketsArrayList) {
            var bracketPosition = BracketPosition(bracket.bracketPosition.row, bracket.bracketPosition.col)
            positionArrayList.add(bracketPosition)
        }
        presenter.createBrackets(positionArrayList)

        for(bracket in bracketsArrayList) {
            var index = findBracketIndex(bracket.bracketPosition)
            presenter.updateBracket(bracket, index)
        }

    }
}