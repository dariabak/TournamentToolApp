package bracket

import com.google.gson.Gson
import com.google.gson.JsonElement
import org.json.*
import java.util.*
import com.google.gson.reflect.TypeToken
import bracket.model.BracketPosition
import bracket.Bracket

interface BracketRepoInterface {
    fun saveTournament(name: String, brackets: ArrayList<Bracket>, numberOfTeams: Int)
    fun getTournament(tournamentName: String): ArrayList<Bracket>
}

class BracketRepo(val service: BracketServiceInterface): BracketRepoInterface {

    override fun saveTournament(name: String, brackets: ArrayList<Bracket>, numberOfTeams: Int) {
        val tournamentDTO = TournamentDTO.create(name, brackets, numberOfTeams)
        service.saveTournament("tournaments.json", tournamentDTO)
    }

    override fun getTournament(tournamentName: String): ArrayList<Bracket> {
        val gson = Gson()
        val tournamentDTO = service.getTournament(tournamentName)
        var bracketsArrayList = tournamentDTO.brackets.map { Bracket(it)}

        return bracketsArrayList.toCollection(ArrayList())
    }
}