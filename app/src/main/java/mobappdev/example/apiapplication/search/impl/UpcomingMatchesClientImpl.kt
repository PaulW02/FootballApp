package mobappdev.example.apiapplication.search.impl

import UpcomingMatches
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mobappdev.example.apiapplication.AbstractJsportsClient
import mobappdev.example.apiapplication.model.ResultResponse
import mobappdev.example.apiapplication.search.SearchEventClient
import java.net.HttpURLConnection

class UpcomingMatchesClientImpl: AbstractJsportsClient()
{
    suspend fun searchUpcomingMatchesById( id: Int): Result<UpcomingMatches> {
        val request = requestBuilder("/eventsnext.php?id=$id")
        val url = request.url.toUrl()
        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }
                // Use Gson to parse the JSON string into a Joke object
                val type = object : TypeToken<UpcomingMatches>() {}.type
                val joke = Gson().fromJson<UpcomingMatches>(json, type)
                Result.success(joke)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}