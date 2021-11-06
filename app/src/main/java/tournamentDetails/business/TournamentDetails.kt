package tournamentDetails.business

import bracket.Bracket
import bracket.TournamentDTO

class TournamentDetails(dto: TournamentDTO) {
    val bracketArrayList: ArrayList<Bracket> = dto.brackets.map { Bracket(it)}.toCollection(java.util.ArrayList())
    val tournamentName: String = dto.name

}