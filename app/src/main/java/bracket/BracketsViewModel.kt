package bracket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BracketsViewModel: ViewModel() {

    private var _winner = MutableLiveData<String>()
    val winner: LiveData<String>
        get() = _winner

    fun setWinner(team: String) {
        _winner.value = team
    }

    private var _bracketId = MutableLiveData<Int>()
    val bracketId: LiveData<Int>
        get() = _bracketId

    fun setBracketId(id: Int) {
        _bracketId.value = id
    }

    override fun onCleared() {
        super.onCleared()
    }
}