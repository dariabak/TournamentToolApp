package tournamentDetails.data

import tournamentDetails.business.TournamentDetails

interface BracketRepoInterface {
    fun saveTournament(tournament: TournamentDetails)
    fun getTournament(tournamentId: String): TournamentDetails
}

class BracketRepo(private val service: BracketServiceInterface): BracketRepoInterface {

    override fun saveTournament(tournament: TournamentDetails) {
        val tournamentDTO = TournamentDTO.createFromTournament(tournament)
        service.saveTournament("tournaments.json", tournamentDTO)
    }

    override fun getTournament(tournamentId: String): TournamentDetails {
        val tournamentDTO = service.getTournament(tournamentId)
        return TournamentDetails(tournamentDTO)
    }
}