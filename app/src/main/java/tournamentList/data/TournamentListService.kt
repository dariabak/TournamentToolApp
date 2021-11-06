package tournamentList

import android.content.Context
import bracket.TournamentDTO
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

interface TournamentListServiceInterface {
    fun getTournamentsNamesJSON(): ArrayList<TournamentDTO>
}

class TournamentListService(val context: Context): TournamentListServiceInterface {

    override fun getTournamentsNamesJSON(): ArrayList<TournamentDTO> {
        var json = ""
        val gson = Gson()
        val file = File(context.getFilesDir(), "tournaments.json")
        if (file.exists()) {
            val inputStream = context.openFileInput("tournaments.json")
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
        }
        return ArrayList<TournamentDTO>()
    }
}