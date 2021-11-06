package tournamentList.business

import bracket.BracketDTO
import bracket.TournamentDTO

class Tournament {
    constructor(tournamentDTO: TournamentDTO){
        this.name = tournamentDTO.name
        this.update = tournamentDTO.date
        this.numberOfTeams = countNumberOfTeams(tournamentDTO.brackets)
        this.id = tournamentDTO.id
    }

    private fun countNumberOfTeams(bracketsArrayList: List<BracketDTO>): Int{
        var number = 0
        val firstColumnRows = (bracketsArrayList.size + 1) / 2
        for(i in 0 until firstColumnRows) {
            if(bracketsArrayList[i].top != ""){
                number++
            }
            if(bracketsArrayList[i].bottom != "") {
                number++
            }
        }
        return number
    }

    var name: String
    var update: String
    var numberOfTeams: Int
    var id: String

}