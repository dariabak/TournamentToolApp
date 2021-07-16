package helpers

import bracket.BracketView
import bracket.model.BracketPosition
import android.view.ViewGroup
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.abs
import kotlin.random.Random

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

        fun positionBrackets(brackets: ArrayList<BracketView>) {
            for(bracket in brackets){
                when(bracket.bracketPosition.col){
                    0 -> {
                        val topMargin = bracket.bracketPosition.row * 200
                        bracket.setLayoutParams(0, topMargin)
                    }
                    else -> {
                        val previousPositions = getPreviousBrackets(bracket.bracketPosition.row, bracket.bracketPosition.col)
                        val previousPosition1 = previousPositions.get(0)
                        val previousPosition2 = previousPositions.get(1)
                        val previousBracket1 = findBracket(previousPosition1, brackets)
                        val previousBracket2 = findBracket(previousPosition2, brackets)
                        var layoutParam1 = previousBracket1.layoutParams as ViewGroup.MarginLayoutParams
                        var topMargin1 = layoutParam1.topMargin
                        var leftMargin = layoutParam1.leftMargin

                        var layoutParam2 = previousBracket2.layoutParams as ViewGroup.MarginLayoutParams
                        var topMargin2 = layoutParam2.topMargin

                        val newMargin = (topMargin1 + topMargin2) / 2
                        bracket.setLayoutParams(leftMargin + (100* MyApp.density).toInt(), newMargin)
                    }

                }
            }

        }

        fun findBracket(position: BracketPosition, brackets: ArrayList<BracketView>): BracketView {
            var previousColumnsRowCounter = 0.0
            for(i in 0 until position.col){
                previousColumnsRowCounter += (brackets.size + 1) / 2.0.pow(i)/2
            }
            return brackets.get(previousColumnsRowCounter.toInt() + position.row)
        }

        fun positionBracketLines(lines: ArrayList<BracketLineView>, brackets: ArrayList<BracketView>) {
            var rowsInFirstColumn = (brackets.size + 1)/2
            var j = 0
            for(i in rowsInFirstColumn until brackets.size) {
                var previousPositions = getPreviousBrackets(brackets.get(i).bracketPosition.row, brackets.get(i).bracketPosition.col)
                val previousPosition1 = previousPositions.get(0)
                val previousPosition2 = previousPositions.get(1)
                val previousBracket1 = findBracket(previousPosition1, brackets)
                val previousBracket2 = findBracket(previousPosition2, brackets)

                var layoutParam1 = previousBracket1.layoutParams as ViewGroup.MarginLayoutParams
                var topMargin1 = layoutParam1.topMargin
                var leftMargin = layoutParam1.leftMargin

                var layoutParam2 = previousBracket2.layoutParams as ViewGroup.MarginLayoutParams
                var topMargin2 = layoutParam2.topMargin

                var layoutParam3 = brackets.get(i).layoutParams as ViewGroup.MarginLayoutParams
                var topMargin3 = layoutParam3.topMargin
                var leftMargin3 = layoutParam3.leftMargin
                var width = leftMargin3 - previousBracket1.getViewWidth() - leftMargin
                var height1 = topMargin3 - topMargin1
                var height2 = topMargin3 - topMargin2
                lines.get(j).setLayoutParams(leftMargin + previousBracket1.getViewWidth(), topMargin1 + previousBracket1.getMidY(), width, height1)
                lines.get(j+1).setLayoutParams(leftMargin + previousBracket2.getViewWidth(), topMargin3 + brackets.get(i).getMidY(), width, abs(height2))
                lines.get(j+1).scaleY = -1f
                j += 2
            }
        }

        fun setTeamsNames(teamNames: ArrayList<String>, brackets: ArrayList<BracketView>) {

            var index = ArrayList<Int>()
            teamNames.shuffle()
            var bracketsInFirstColumn = (brackets.size + 1)/2
            for(i in bracketsInFirstColumn - 1 downTo 0) {
                brackets.get(i).setTeam1(teamNames.get(i))
                teamNames.removeAt(i)
                index.add(i)
            }

            index.shuffle()
            for(team in teamNames){
                brackets.get(index.get(0)).setTeam2(team)
                index.remove(index.get(0))
            }

        }

        fun findNextBracket(bracketId: Int, brackets: ArrayList<BracketView>): Int {
            var firstColumn = (brackets.size + 1)/2
            var nextBracketId = floor(bracketId.toDouble()/2.0) + firstColumn
            return nextBracketId.toInt()
        }

    }
}