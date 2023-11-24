package mobappdev.example.apiapplication.networking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mobappdev.example.apiapplication.data.WeatherDaily
import mobappdev.example.apiapplication.data.WeatherDetails
import java.net.HttpURLConnection
import java.net.URL
import mobappdev.example.apiapplication.utils.Result

object WeatherDataSource {
    private const val hourlyURL = "https://api.open-meteo.com/v1/forecast?latitude=59.3294&longitude=18.0687&hourly=temperature_2m&forecast_days=1"
    private const val dailyURL = "https://api.open-meteo.com/v1/forecast?latitude=59.3294&longitude=18.0687&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset"

    suspend fun getStockholmHourlyWeather(): Result<WeatherDetails> {
        val urlString = hourlyURL
        val url = URL(urlString)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                // Use Gson to parse the JSON string into a Joke object
                val type = object : TypeToken<WeatherDetails>() {}.type
                val joke = Gson().fromJson<WeatherDetails>(json, type)

                Result.Success(joke)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getStockholmDailyWeather(): Result<WeatherDaily> {
        val urlString = dailyURL
        val url = URL(urlString)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                // Use Gson to parse the JSON string into a Joke object
                val type = object : TypeToken<WeatherDaily>() {}.type
                val joke = Gson().fromJson<WeatherDaily>(json, type)

                Result.Success(joke)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getStockholmTodayWeather(): Result<WeatherDetails> {
        val urlString = hourlyURL
        val url = URL(urlString)

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                // Use Gson to parse the JSON string into a Joke object
                val type = object : TypeToken<WeatherDetails>() {}.type
                val joke = Gson().fromJson<WeatherDetails>(json, type)

                Result.Success(joke)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
