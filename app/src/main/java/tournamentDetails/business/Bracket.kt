package bracket

import tournamentDetails.business.BracketPosition

enum class Winner {
    TOP, BOTTOM, NONE
}

class Bracket(val bracketPosition: BracketPosition) {

    constructor(dto: BracketDTO): this(BracketPosition(dto.bracketPosition.row, dto.bracketPosition.col)) {
        this.team1 = dto.top
        this.team2 = dto.bottom
        this.winner = Winner.valueOf(dto.winner)
    }
    var team1 = ""
    var team2 = ""
    var winner = Winner.NONE
}