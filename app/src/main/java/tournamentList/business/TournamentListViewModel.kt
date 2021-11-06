package tournamentList.business

import androidx.lifecycle.ViewModel
import tournamentList.TournamentListRepoInterface

class TournamentListViewModel(val repo: TournamentListRepoInterface): ViewModel() {

    fun getTournamentsMap(): ArrayList<Tournament> {
        return repo.getTournamentsMap()
    }
}