package tournamentList

import android.content.Context
import org.json.JSONArray
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

interface TournamentListServiceInterface {
    fun getTournamentsNamesJSON(): JSONArray
}

class TournamentListService(val context: Context): TournamentListServiceInterface {

    override fun getTournamentsNamesJSON(): JSONArray {
        var json = ""
        var jsonArray = JSONArray()
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
            jsonArray = JSONArray(json)
        }
        return jsonArray
    }
}