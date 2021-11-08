package tournamentDetails.business

import bracket.Bracket
import bracket.TournamentDTO

class TournamentDetails(val bracketsArrayList: ArrayList<Bracket>, val name: String, val id: String) {

    constructor(dto: TournamentDTO): this(dto.brackets.map { Bracket(it)}.toCollection(java.util.ArrayList()), dto.name, dto.id)


}