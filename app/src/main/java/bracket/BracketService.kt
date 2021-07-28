package bracket

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.*


interface BracketServiceInterface {
    fun saveTournament(fileName: String, jsonObject: JSONObject, context: Context)
}

class BracketService : BracketServiceInterface {

    fun getJsonArray(fileName: String, context: Context): JSONArray {
        var json = ""
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
            return JSONArray(json)
        } else {
            return JSONArray()
        }
    }

    override fun saveTournament(fileName: String, jsonObject: JSONObject, context: Context) {
        var jsonArray = getJsonArray(fileName, context)
        jsonArray?.put(jsonObject)
        var jsonString = jsonArray.toString()
        val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        fileOutputStream.write(jsonString.toByteArray())
    }
}