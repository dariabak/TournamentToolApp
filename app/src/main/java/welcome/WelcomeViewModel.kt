package welcome

import androidx.lifecycle.ViewModel
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.widget.*

class WelcomeViewModel: ViewModel() {
    var numberOfTeamsArray = arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 12, 13)
    var typesOfScheduleArray = arrayOf("Tournament Single Elimination")
    var teamsNames: MutableMap<Int, String> = mutableMapOf()
    var numberOfTeams = 0


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

    fun getArrayOfNames(): Array<String> {
        var array = Array<String>(numberOfTeams, {i -> "a"})
        for(i in 0 until numberOfTeams){
            if(teamsNames.containsKey(i)) {
                if(teamsNames.get(i) != ""){
                    array[i] = teamsNames.get(i).toString()
                } else {
                    var num = i + 1
                    array[i] = "Team $num"
                }

            } else {
                var num = i + 1
                array[i] = "Team $num"
            }
        }
        return array
    }
}