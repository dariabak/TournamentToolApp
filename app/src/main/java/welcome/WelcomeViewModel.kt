package welcome

import androidx.lifecycle.ViewModel
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.widget.*

class WelcomeViewModel: ViewModel() {
    var numberOfTeamsArray = arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 12, 13)
    var typesOfScheduleArray = arrayOf("Tournament Single Elimination")
    var teamsNames = ArrayList<String>()


    private var _chosenNumber = MutableLiveData<Int>()
    val chosenNumber: LiveData<Int>
        get() = _chosenNumber

    private var _switchOn = MutableLiveData<Boolean>()
    val switchOn: LiveData<Boolean>
        get() = _switchOn
   init{
       _chosenNumber.value = 0
       _switchOn.value = false
   }

    fun setChosenNumber(number: Int) {
        _chosenNumber.value = number
    }
    fun switchOn() {
        _switchOn.value = true
    }
    fun switchOff() {
        _switchOn.value = false
    }
//    fun getTeamNames(): Array<String> {
//        var name = teamsNames.get(0).text.toString()
//        var namesArray = arrayOfNulls<String>(teamsNames.size)
//            for(i in 0..teamsNames.size-1) {
//              // namesArray[i] = teamsNames.get(i).text
//            }
//
//        return namesArray
//    }
}