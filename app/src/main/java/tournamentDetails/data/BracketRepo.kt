package bracket

import com.google.gson.Gson
import java.util.*

interface BracketRepoInterface {
    fun saveTournament(name: String, brackets: ArrayList<Bracket>, numberOfTeams: Int, tournamentId: String)
    fun getTournament(tournamentId: String): ArrayList<Bracket>
}

class BracketRepo(val service: BracketServiceInterface): BracketRepoInterface {

    override fun saveTournament(name: String, brackets: ArrayList<Bracket>, numberOfTeams: Int, tournamentId: String) {
        val tournamentDTO = TournamentDTO.create(name, brackets, numberOfTeams, tournamentId)
        service.saveTournament("tournaments.json", tournamentDTO)
    }

    override fun getTournament(tournamentId: String): ArrayList<Bracket> {
        val gson = Gson()
        val tournamentDTO = service.getTournament(tournamentId)
        var bracketsArrayList = tournamentDTO.brackets.map { Bracket(it)}

        return bracketsArrayList.toCollection(ArrayList())
    }
}