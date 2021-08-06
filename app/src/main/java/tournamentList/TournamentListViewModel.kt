package tournamentList

import androidx.lifecycle.ViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import android.content.Context

class TournamentListViewModel(val repo: TournamentListRepoInterface): ViewModel() {

    fun getTournamentsMap(): ArrayList<Tournament> {
        return repo.getTournamentsMap()
    }
}