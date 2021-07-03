package helpers

import bracket.BracketsView
import bracket.model.BracketPosition
import android.view.ViewGroup
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
            val previousBracketPosition1 = BracketPosition(2 * row, col - 1)
            val previousBracketPosition2 = BracketPosition(2 * row + 1, col - 1)
            previousBracketPositionArrayList.add(previousBracketPosition1)
            previousBracketPositionArrayList.add(previousBracketPosition2)

            return previousBracketPositionArrayList
        }

        fun positionBrackets(brackets: ArrayList<BracketsView>){
            for(bracket in brackets){
                when(bracket.bracketPosition.col){
                    0 -> {
                        val topMargin = bracket.bracketPosition.row*200
                        bracket.setLayoutParams(0,topMargin)
                    }
                    else -> {
                        val previousPositions = getPreviousBrackets(bracket.bracketPosition.row, bracket.bracketPosition.col)
                        val previousPosition1 = previousPositions.get(0)
                        val previousPosition2 = previousPositions.get(1)
                        val previousBracket1 = findBracket(previousPosition1, brackets)
                        val previousBracket2 = findBracket(previousPosition2, brackets)

                        var layoutParam1 = previousBracket1.layoutParams as  ViewGroup.MarginLayoutParams
                        var topMargin1 = layoutParam1.topMargin
                        var leftMargin = layoutParam1.leftMargin

                        var layoutParam2 = previousBracket2.layoutParams as  ViewGroup.MarginLayoutParams
                        var topMargin2 = layoutParam2.topMargin

//                        topMargin1 += bracket.getMidY()
//                        topMargin2 += bracket.getMidY()

                        val newMargin = (topMargin1 + topMargin2)/2
                        bracket.setLayoutParams(leftMargin + 200, newMargin)


                    }

                }
            }
        }

        fun findBracket(position: BracketPosition, brackets: ArrayList<BracketsView>): BracketsView {
            val power = (position.col + 1).toDouble()
            var value = (brackets.size + 1) / 2.0.pow(power)
            var previousColumnsRowCounter = 0.0
            for(i in 0 until position.col){
                previousColumnsRowCounter += (brackets.size + 1) / 2.0.pow(i)/2
            }
            return brackets.get(previousColumnsRowCounter.toInt() + position.row)
        }
    }
}