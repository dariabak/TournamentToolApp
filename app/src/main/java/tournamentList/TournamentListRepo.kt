package tournamentList

interface TournamentListRepoInterface {
    fun getTournamentsMap(): MutableMap<String, Pair<String, Int>>
}

class TournamentListRepo(val service: TournamentListServiceInterface): TournamentListRepoInterface {

    override fun getTournamentsMap(): MutableMap<String, Pair<String, Int>> {
        var tournamentsMap = mutableMapOf<String, Pair<String, Int>>()
        val tournamentDTOArray = service.getTournamentsNamesJSON()

        for(i in tournamentDTOArray.size - 1 downTo 0) {
            var tournamentDTO = tournamentDTOArray.get(i)
            var dateAndNumber = Pair(tournamentDTO.date, tournamentDTO.numberOfTeams)
            tournamentsMap.put(tournamentDTO.name, dateAndNumber)
        }

        return tournamentsMap
    }
}