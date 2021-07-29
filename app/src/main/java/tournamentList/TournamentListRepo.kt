package tournamentList

import tournamentList.TournamentListServiceInterface

interface TournamentListRepoInterface {
    fun getTournamentsNamesArrayList(): ArrayList<String>
}

class TournamentListRepo(val service: TournamentListServiceInterface): TournamentListRepoInterface {

    override fun getTournamentsNamesArrayList(): ArrayList<String> {
        var tournamentsNamesArrayList = ArrayList<String>()
        val jsonArray = service.getTournamentsNamesJSON()

        for(i in 0 until jsonArray.length()) {
            var item = jsonArray.getJSONObject(i)
            tournamentsNamesArrayList.add(item.get("name").toString())
        }

        return tournamentsNamesArrayList
    }
}