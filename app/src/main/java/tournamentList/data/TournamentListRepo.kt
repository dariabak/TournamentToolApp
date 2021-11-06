package tournamentList

import tournamentList.business.Tournament

interface TournamentListRepoInterface {
    fun getTournamentsMap(): ArrayList<Tournament>
}

class TournamentListRepo(val service: TournamentListServiceInterface): TournamentListRepoInterface {

    override fun getTournamentsMap(): ArrayList<Tournament> {
        var tournamentsArrayList = ArrayList<Tournament>()
        val tournamentDTOArray = service.getTournamentsNamesJSON()

        for(i in tournamentDTOArray.size - 1 downTo 0) {
            var tournamentDTO = tournamentDTOArray.get(i)
            var tournament = Tournament(tournamentDTO)
            tournamentsArrayList.add(tournament)
        }

        return tournamentsArrayList
    }
}