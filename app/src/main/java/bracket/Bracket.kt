package bracket

import bracket.model.BracketPosition

enum class Winner {
    TOP, BOTTOM, NONE
}

class Bracket(val bracketPosition: BracketPosition) {
    var team1 = ""
    var team2 = ""
    var winner = Winner.NONE
}