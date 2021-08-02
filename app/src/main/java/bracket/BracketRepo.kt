package bracket

import android.content.Context
import org.json.JSONObject
import org.json.*
import bracket.BracketServiceInterface

interface BracketRepoInterface {
    fun saveTournament(name: String, brackets: ArrayList<Bracket>)
}

class BracketRepo(val service: BracketServiceInterface): BracketRepoInterface {

    override fun saveTournament(name: String, brackets: ArrayList<Bracket>) {
        var bracketsList = mutableListOf<MutableMap<String, Any>>()
        for (i in 0 until brackets.size) {
            var mapSingleBracket = mutableMapOf<String, Any>()
            mapSingleBracket.put("bracketTeam1", brackets.get(i).team1)
            mapSingleBracket.put("bracketTeam2", brackets.get(i).team2)
            mapSingleBracket.put("bracketWinner", brackets.get(i).winner.toString())
            val bracketPosition = mutableMapOf<String, Int>()
            bracketPosition.put("col", brackets.get(i).bracketPosition.col)
            bracketPosition.put("row", brackets.get(i).bracketPosition.row)
            mapSingleBracket.put("bracketPosition", bracketPosition)
            bracketsList.add(mapSingleBracket)
        }

        var jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("brackets", bracketsList)
        service.saveTournament("tournaments.json", jsonObject)
    }
}