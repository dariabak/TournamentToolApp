package tournamentList

interface TournamentListRepoInterface {
    fun getTournamentsMap(): MutableMap<String, String>
}

class TournamentListRepo(val service: TournamentListServiceInterface): TournamentListRepoInterface {

    override fun getTournamentsMap(): MutableMap<String, String> {
        var tournamentsMap = mutableMapOf<String, String>()
        val tournamentDTOArray = service.getTournamentsNamesJSON()

        for(i in tournamentDTOArray.size - 1 downTo 0) {
            var tournamentDTO = tournamentDTOArray.get(i)
            tournamentsMap.put(tournamentDTO.name, tournamentDTO.date)
        }

        return tournamentsMap
    }
}