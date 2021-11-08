package bracket

import tournamentDetails.business.BracketPosition
import tournamentDetails.business.TournamentDetails
import tournamentDetails.data.BracketRepoInterface
import tournamentList.business.Tournament
import kotlin.math.floor
import kotlin.math.pow
import java.math.BigDecimal


interface BracketInteractorInterface{
 fun setUpBrackets(teamNames: ArrayList<String>, positionArrayList: ArrayList<BracketPosition>, tournamentName: String, tournamentId: String)
 fun showBracketBottomSheet(position: BracketPosition)
 fun saveTournament()
 fun getTournament(tournamentId: String)
}

class BracketInteractor(private val presenter: BracketPresenterInterface, private val repo: BracketRepoInterface): BracketInteractorInterface {
    var bracketsArrayList = ArrayList<Bracket>()
    lateinit var tournament: TournamentDetails

    override fun setUpBrackets(teamNames: ArrayList<String>, positionArrayList: ArrayList<BracketPosition>, tournamentName: String, tournamentId: String) {
        for(element in positionArrayList) {
            var bracket = Bracket(element)
            bracketsArrayList.add(bracket)
        }
        tournament = TournamentDetails(bracketsArrayList, tournamentName, tournamentId)
        setTeamsNames(teamNames, tournament.bracketsArrayList)
        for(bracket in tournament.bracketsArrayList) {
            var index = findBracketIndex(bracket.bracketPosition)
            presenter.updateBracket(bracket, index)
            if(bracket.bracketPosition.col == 0) {
                if (bracket.team2 == "") {
                    updateWinner(tournament.bracketsArrayList.get(index), Winner.TOP)
                } else if (bracket.team1 == "") {
                    updateWinner(tournament.bracketsArrayList.get(index), Winner.BOTTOM)
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
            brackets[index[0]].team2 = team
            index.remove(index[0])
        }
    }
    override fun showBracketBottomSheet(position: BracketPosition) {
        val index = findBracketIndex(position)
        val bracket = tournament.bracketsArrayList.get(index)

        presenter.presentBracket(bracket) { winner ->
            updateWinner(bracket, winner)
        }
    }

    private fun updateWinner(bracket: Bracket, winner: Winner) {
        bracket.winner = winner
        val currentBracketIndex = findBracketIndex(bracket.bracketPosition)
        tournament.bracketsArrayList.get(currentBracketIndex).winner = winner
        presenter.updateBracket(bracket, currentBracketIndex)
        if(!isLastBracket(currentBracketIndex)){
            val nextBracketIndex = getNextBracketIndex(currentBracketIndex)
                if (isTop(currentBracketIndex)) {
                    if(bracket.winner == Winner.TOP) {
                        tournament.bracketsArrayList.get(nextBracketIndex).team1 = bracket.team1
                    } else {
                        tournament.bracketsArrayList.get(nextBracketIndex).team1 = bracket.team2
                    }
                } else {
                    if(bracket.winner == Winner.BOTTOM) {
                       tournament.bracketsArrayList.get(nextBracketIndex).team2 = bracket.team2
                    } else {
                        tournament.bracketsArrayList.get(nextBracketIndex).team2 = bracket.team1
                    }
                }
            presenter.updateBracket(tournament.bracketsArrayList.get(nextBracketIndex), nextBracketIndex)
            var isDone = false
            var nextIndex = nextBracketIndex
            var previousIndex = nextIndex
            var isFirst = true
            while(!isDone) {
                if(isFirst) {
                    tournament.bracketsArrayList.get(nextIndex).winner = Winner.NONE
                    presenter.updateBracket(
                        tournament.bracketsArrayList.get(nextIndex),
                        nextIndex
                        )
                } else {
                   tournament.bracketsArrayList[nextIndex].winner = Winner.NONE
                    if(isTop(previousIndex)) {
                        tournament.bracketsArrayList[nextIndex].team1 = ""
                    } else {
                        tournament.bracketsArrayList[nextIndex].team2 = ""
                    }
                    presenter.updateBracket(
                        tournament.bracketsArrayList[nextIndex],
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

    private fun findBracketIndex(position: BracketPosition): Int {
        var previousColumnsRowCounter = 0.0
        for(i in 0 until position.col){
            previousColumnsRowCounter += (tournament.bracketsArrayList.size + 1) / 2.0.pow(i)/2
        }
        return previousColumnsRowCounter.toInt() + position.row
    }

    fun getNextBracketIndex(index: Int): Int {
        var firstColumn = (tournament.bracketsArrayList.size + 1)/2
        var nextBracketId = floor(index.toDouble()/2.0) + firstColumn
        return nextBracketId.toInt()
    }
    private fun isLastBracket(index: Int): Boolean {
        return tournament.bracketsArrayList.size - 1 == index
    }
    private fun isTop(index: Int): Boolean {
        val a = BigDecimal("$index")
        val b = BigDecimal("2")
        return (a.rem(b).toInt() == 0)
    }

    override fun saveTournament() {
        repo.saveTournament(tournament)
    }

    override fun getTournament(tournamentId: String){
        tournament = repo.getTournament(tournamentId)
        val positionArrayList = ArrayList<BracketPosition>()
        for(bracket in tournament.bracketsArrayList) {
            val bracketPosition = BracketPosition(bracket.bracketPosition.row, bracket.bracketPosition.col)
            positionArrayList.add(bracketPosition)
        }
        presenter.createBrackets(positionArrayList)

        for(bracket in tournament.bracketsArrayList) {
            val index = findBracketIndex(bracket.bracketPosition)
            presenter.updateBracket(bracket, index)
        }
        presenter.updateTournamentName(tournament.name)
    }
}