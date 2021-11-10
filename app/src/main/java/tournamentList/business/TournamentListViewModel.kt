package tournamentList.business

import androidx.lifecycle.ViewModel
import tournamentList.data.TournamentListRepoInterface

class TournamentListViewModel(private val repo: TournamentListRepoInterface): ViewModel() {

    fun getTournamentsMap(): ArrayList<Tournament> {
        return repo.getTournamentsMap()
    }
}