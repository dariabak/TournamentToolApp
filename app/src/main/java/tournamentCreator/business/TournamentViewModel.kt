package tournamentCreator.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TournamentViewModel: ViewModel() {
    var numberOfTeamsArray = Array(31) { i -> i + 2}
    var typesOfScheduleArray = arrayOf("Tournament Single Elimination")
    var teamsNames: MutableMap<Int, String> = mutableMapOf()
    var numberOfTeams = 0
    var tournamentName = ""

    private var _canContinue = MutableLiveData<Boolean>()
    val canContinue: LiveData<Boolean>
        get() = _canContinue

    private var _chosenNumber = MutableLiveData<Int>()
    val chosenNumber: LiveData<Int>
        get() = _chosenNumber

    private var _switchOn = MutableLiveData<Boolean>()
    val switchOn: LiveData<Boolean>
        get() = _switchOn
   init{
       _chosenNumber.value = 0
       _switchOn.value = false
       _canContinue.value = false
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

    fun checkTournamentNameLength() {
        _canContinue.value = tournamentName.isNotEmpty()
    }

    fun getArrayOfNames(): Array<String> {
        var array = Array<String>(numberOfTeams) { i -> "a" }
        for(i in 0 until numberOfTeams){
            if(teamsNames.containsKey(i)) {
                if(teamsNames[i] != ""){
                    array[i] = teamsNames[i].toString()
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