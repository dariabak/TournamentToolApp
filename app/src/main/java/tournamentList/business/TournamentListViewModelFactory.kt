package tournamentList.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tournamentList.TournamentListRepoInterface

class TournamentListViewModelFactory(private val repo: TournamentListRepoInterface): ViewModelProvider.NewInstanceFactory() {

    override fun <T: ViewModel?> create(modelClass:Class<T>): T {
        return TournamentListViewModel(repo) as T
    }
}