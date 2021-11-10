package tournamentList.data

import tournamentList.business.Tournament

interface TournamentListRepoInterface {
    fun getTournamentsMap(): ArrayList<Tournament>
}

class TournamentListRepo(private val service: TournamentListServiceInterface): TournamentListRepoInterface {

    override fun getTournamentsMap(): ArrayList<Tournament> {
        var tournamentsArrayList = ArrayList<Tournament>()
        val tournamentDTOArray = service.getTournamentsNamesJSON()

        for(i in tournamentDTOArray.size - 1 downTo 0) {
            var tournamentDTO = tournamentDTOArray[i]
            var tournament = Tournament(tournamentDTO)
            tournamentsArrayList.add(tournament)
        }

        return tournamentsArrayList
    }
}