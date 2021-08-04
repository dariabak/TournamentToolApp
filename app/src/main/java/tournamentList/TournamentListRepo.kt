package tournamentList

import tournamentList.TournamentListServiceInterface
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import bracket.TournamentDTO

interface TournamentListRepoInterface {
    fun getTournamentsNamesArrayList(): ArrayList<String>
}

class TournamentListRepo(val service: TournamentListServiceInterface): TournamentListRepoInterface {

    override fun getTournamentsNamesArrayList(): ArrayList<String> {
        var tournamentsNamesArrayList = ArrayList<String>()
        val tournamentDTOArray = service.getTournamentsNamesJSON()

        for(i in tournamentDTOArray.size - 1 downTo 0) {
            var tournamentDTO = tournamentDTOArray.get(i)
            tournamentsNamesArrayList.add(tournamentDTO.name)
        }

        return tournamentsNamesArrayList
    }
}