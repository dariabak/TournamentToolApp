package tournamentDetails.data

import bracket.Bracket
import bracket.BracketServiceInterface
import bracket.TournamentDTO
import tournamentDetails.business.TournamentDetails
import java.util.*

interface BracketRepoInterface {
    fun saveTournament(name: String, brackets: ArrayList<Bracket>,tournamentId: String)
    fun getTournament(tournamentId: String): TournamentDetails
}

class BracketRepo(private val service: BracketServiceInterface): BracketRepoInterface {

    override fun saveTournament(name: String, brackets: ArrayList<Bracket>, tournamentId: String) {
        val tournamentDTO = TournamentDTO.create(name, brackets, tournamentId)
        service.saveTournament("tournaments.json", tournamentDTO)
    }

    override fun getTournament(tournamentId: String): TournamentDetails {
        val tournamentDTO = service.getTournament(tournamentId)
        return TournamentDetails(tournamentDTO)
    }
}