package bracket

import com.google.gson.Gson
import com.google.gson.JsonElement
import org.json.*
import java.util.*
import com.google.gson.reflect.TypeToken
import bracket.model.BracketPosition

interface BracketRepoInterface {
    fun saveTournament(name: String, brackets: ArrayList<Bracket>)
    fun getTournament(tournamentName: String): ArrayList<Bracket>
}

class BracketRepo(val service: BracketServiceInterface): BracketRepoInterface {

    override fun saveTournament(name: String, brackets: ArrayList<Bracket>) {
        val tournamentDTO = TournamentDTO.create(name, brackets)
        service.saveTournament("tournaments.json", tournamentDTO)
    }

    override fun getTournament(tournamentName: String): ArrayList<Bracket> {
        val gson = Gson()
        val tournamentDTO = service.getTournament(tournamentName)
        val bracketsArrayList = ArrayList<Bracket>()

        for(bracketDTO in tournamentDTO.brackets){
            var bracketPosition = BracketPosition(bracketDTO.bracketPosition.row, bracketDTO.bracketPosition.col)
            var bracket = Bracket(bracketPosition)
            bracket.team1 = bracketDTO.top
            bracket.team2 = bracketDTO.bottom
            bracket.winner = Winner.valueOf(bracketDTO.winner)
            bracketsArrayList.add(bracket)
        }
    return bracketsArrayList
    }
}