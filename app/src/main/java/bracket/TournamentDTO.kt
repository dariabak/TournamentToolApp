package bracket

import com.google.gson.annotations.SerializedName

class TournamentDTO {
    companion object {
        fun create(name: String, brackets: List<Bracket>): TournamentDTO {
            val tournamentDTO = TournamentDTO()
            tournamentDTO.name = name
            tournamentDTO.brackets = brackets.map { BracketDTO.create(it)}
            return tournamentDTO
        }
    }
    @SerializedName("name")
    var name: String = ""

    @SerializedName("brackets")
    var brackets: List<BracketDTO> = listOf()

}

class BracketDTO {
    companion object {
        fun create(bracket: Bracket): BracketDTO {
            val bracketDTO = BracketDTO()
            bracketDTO.top = bracket.team1
            bracketDTO.bottom = bracket.team2
            bracketDTO.winner = bracket.winner.toString()
            bracketDTO.bracketPosition.col = bracket.bracketPosition.col
            bracketDTO.bracketPosition.row = bracket.bracketPosition.row
            return bracketDTO
        }
    }
    @SerializedName("top")
    var top: String = ""

    @SerializedName("bottom")
    var bottom: String = ""

    @SerializedName("winner")
    var winner: String = ""

    @SerializedName("bracketPosition")
    var bracketPosition: BracketPositionDTO = BracketPositionDTO()
}


class BracketPositionDTO {
    @SerializedName("col")
    var col: Int = -1

    @SerializedName("row")
    var row: Int = -1
}