package bracket

import android.content.Context
import org.json.JSONObject
import org.json.*
import bracket.BracketServiceInterface

interface BracketRepoInterface {
    fun saveTournament(name: String, brackets: ArrayList<Bracket>, context: Context)
}

class BracketRepo: BracketRepoInterface {
val bracketService: BracketServiceInterface = BracketService()
    override fun saveTournament(name: String, brackets: ArrayList<Bracket>, context: Context) {
        var bracketsList = mutableListOf<MutableMap<String, Any>>()
        for (i in 0 until brackets.size) {
            var mapSingleBracket = mutableMapOf<String, Any>()
            mapSingleBracket.put("BracketTeam1", brackets.get(i).team1)
            mapSingleBracket.put("BracketTeam2", brackets.get(i).team2)
            mapSingleBracket.put("BracketWinner", brackets.get(i).winner.toString())
            val bracketPosition = mutableMapOf<String, Int>()
            bracketPosition.put("Col", brackets.get(i).bracketPosition.col)
            bracketPosition.put("Row", brackets.get(i).bracketPosition.row)
            mapSingleBracket.put("BracketPosition", bracketPosition)
            bracketsList.add(mapSingleBracket)
        }

        var jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("brackets", bracketsList)
        bracketService.saveTournament("tournaments.json", jsonObject, context)
    }
}