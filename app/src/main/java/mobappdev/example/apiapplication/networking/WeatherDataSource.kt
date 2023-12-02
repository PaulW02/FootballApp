package mobappdev.example.apiapplication.networking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mobappdev.example.apiapplication.data.CurrentData
import mobappdev.example.apiapplication.data.DailyData
import mobappdev.example.apiapplication.data.WeatherCurrent
import mobappdev.example.apiapplication.data.WeatherDaily
import mobappdev.example.apiapplication.data.WeatherDetails
import java.net.HttpURLConnection
import java.net.URL
import mobappdev.example.apiapplication.utils.Result
import org.json.JSONObject

object WeatherDataSource {
    private const val hourlyURL = "https://api.open-meteo.com/v1/forecast?latitude=59.3294&longitude=18.0687&hourly=temperature_2m,weather_code&forecast_days=1"
    private const val dailyURL = "https://api.open-meteo.com/v1/forecast?latitude=59.3294&longitude=18.0687&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset"
    private const val currentURL = "https://api.open-meteo.com/v1/forecast?latitude=59.3294&longitude=18.0687&current=temperature_2m,is_day,rain&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset&forecast_days=1"

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

    suspend fun getCurrentWeather(latitude: Double, longitude: Double): Result<WeatherCurrent> {
        val baseUrl = "https://api.open-meteo.com/v1/forecast"

        val url = URL("$baseUrl?latitude=$latitude&longitude=$longitude&current=temperature_2m,is_day,rain&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset&forecast_days=1")

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                // Parse only the "current" field from the JSON
                val currentJson = JSONObject(json).getJSONObject("current")
                val dailyJson = JSONObject(json).getJSONObject("daily")

                // Use Gson to parse the "current" JSON object into a WeatherCurrent object
                val currentType = object : TypeToken<CurrentData>() {}.type
                val dailyType = object : TypeToken<DailyData>() {}.type
                val currentWeather = Gson().fromJson<CurrentData>(currentJson.toString(), currentType)
                val todaysWeather = Gson().fromJson<DailyData>(dailyJson.toString(), dailyType)
                val weatherCurrent = WeatherCurrent(currentWeather, todaysWeather)
                Result.Success(weatherCurrent)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    suspend fun getWeatherForWeek(latitude: Double, longitude: Double): Result<WeatherDaily> {

        val baseUrl = "https://api.open-meteo.com/v1/forecast"

        val url = URL("$baseUrl?latitude=$latitude&longitude=$longitude&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset")

        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                // Use Gson to parse the JSON string into a WeatherDaily object
                val type = object : TypeToken<WeatherDaily>() {}.type
                val weatherDaily = Gson().fromJson<WeatherDaily>(json, type)

                Result.Success(weatherDaily)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    suspend fun getStockholmTodayWeather(latitude: Double, longitude: Double): Result<WeatherDetails> {
        val baseUrl = "https://api.open-meteo.com/v1/forecast"

        val url = URL("$baseUrl?latitude=$latitude&longitude=$longitude&hourly=temperature_2m,weather_code&forecast_days=1")


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
