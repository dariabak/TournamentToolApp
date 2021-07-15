package welcome

import androidx.lifecycle.ViewModel
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WelcomeViewModel: ViewModel() {
    var numberOfTeamsArray = arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 12, 13)
    var typesOfScheduleArray = arrayOf("Tournament Single Elimination")
    var teamsNames = ArrayList<View>()

    private var _chosenNumber = MutableLiveData<Int>()
    val chosenNumber: LiveData<Int>
        get() = _chosenNumber

    fun setChosenNumber(number: Int) {
        _chosenNumber.value = number
    }
}