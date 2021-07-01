package helpers

import bracket.model.BracketPosition
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow

class BracketHelper {
    companion object{
        fun getBracketPosition(numberOfTeams: Int): ArrayList<BracketPosition> {
            var bracketsPositionArrayList = ArrayList<BracketPosition>()
            var log = log(numberOfTeams.toDouble(), 2.0)
            var x = ceil(log) - 1.0
            var numberOfBrackets = 2.0.pow(x).toInt()
            var col = 0

            do{
                for(i in 0..numberOfBrackets - 1) {

                    val bracketPosition = BracketPosition(i, col)
                    bracketsPositionArrayList.add(bracketPosition)
                }
                if(numberOfBrackets != 1) {
                    numberOfBrackets = numberOfBrackets / 2
                } else {
                    numberOfBrackets = 0
                }
                col += 1
            } while(numberOfBrackets != 0)
         return bracketsPositionArrayList
        }

        fun getPreviousBrackets(row: Int, col: Int): ArrayList<BracketPosition> {
            var previousBracketPositionArrayList = ArrayList<BracketPosition>()
            if(col != 0) {
                val previousBracketPosition1 = BracketPosition(2 * row, col - 1)
                val previousBracketPosition2 = BracketPosition(2 * row + 1, col - 1)
                previousBracketPositionArrayList.add(previousBracketPosition1)
                previousBracketPositionArrayList.add(previousBracketPosition2)
            }
            return previousBracketPositionArrayList
        }
    }
}