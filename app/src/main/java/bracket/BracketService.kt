package bracket

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.*


interface BracketServiceInterface {
    fun saveTournament(fileName: String, tournamentDTO: TournamentDTO)
    fun getTournament(tournamentName: String): TournamentDTO
}

class BracketService(val context: Context) : BracketServiceInterface {

    fun getJsonArray(fileName: String): ArrayList<TournamentDTO> {
        var json = ""
        val gson = Gson()
        val file = File(context.getFilesDir(), fileName)
        if (file.exists()) {
            val inputStream = context.openFileInput(fileName)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var receiveString: String? = ""
            val stringBuilder = StringBuilder()
            while (bufferedReader.readLine().also { receiveString = it } != null) {
                stringBuilder.append(receiveString)
            }
            json = stringBuilder.toString()
            val tournamentDtoType = object : TypeToken<ArrayList<TournamentDTO>>() {}.type
            val tournamentDTOArray: ArrayList<TournamentDTO> = gson.fromJson(json, tournamentDtoType)
            return tournamentDTOArray
        } else {
            return ArrayList<TournamentDTO>()
        }
    }

    override fun saveTournament(fileName: String, tournamentDTO: TournamentDTO) {
        val gson = Gson()
        var tournamentDTOArray: ArrayList<TournamentDTO> = getJsonArray(fileName)
        tournamentDTOArray.add(tournamentDTO)
        var jsonString = gson.toJson(tournamentDTOArray)
        val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        fileOutputStream.write(jsonString.toByteArray())
    }

    override fun getTournament(tournamentName: String): TournamentDTO {
        var tournamentDTOArray = getJsonArray("tournaments.json")
        for(tournament in tournamentDTOArray){
            if(tournament.name == tournamentName) {
                return tournament
            }
        }
        return TournamentDTO()
    }
}