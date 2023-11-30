package mobappdev.example.apiapplication.data
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object WeatherStorage {
    private const val PREFS_NAME = "WeatherPrefs"
    private const val KEY_WEATHER = "saved_weather"





    fun saveWeather(context: Context, weatherData: WeatherDaily?) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(weatherData)
        editor.putString(KEY_WEATHER, json)
        editor.apply()
    }

    fun loadWeather(context: Context): WeatherDaily? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(KEY_WEATHER, null)
        return gson.fromJson(json, object : TypeToken<WeatherDaily?>() {}.type)
    }

    private const val PREFS_NAME_DETAILS = "WeatherDetailsPrefs"
    private const val KEY_WEATHER_DETAILS = "savedWeatherDetails"

    fun saveWeatherDetails(context: Context, weatherDetails: WeatherDetails?) {
        val prefs = context.getSharedPreferences(PREFS_NAME_DETAILS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(weatherDetails)
        editor.putString(KEY_WEATHER_DETAILS, json)
        editor.apply()
    }

    fun loadWeatherDetails(context: Context): WeatherDetails? {
        val prefs = context.getSharedPreferences(PREFS_NAME_DETAILS, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(KEY_WEATHER_DETAILS, null)
        return gson.fromJson(json, object : TypeToken<WeatherDetails?>() {}.type)
    }

    private const val PREFS_NAME_CURRENT = "WeatherCurrentPrefs"
    private const val KEY_WEATHER_CURRENT = "savedWeatherCurrent"

    fun saveWeatherCurrent(context: Context, weatherCurrent: WeatherCurrent?) {
        val prefs = context.getSharedPreferences(PREFS_NAME_CURRENT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(weatherCurrent)
        editor.putString(KEY_WEATHER_CURRENT, json)
        editor.apply()
    }

    fun loadWeatherCurrent(context: Context): WeatherCurrent? {
        val prefs = context.getSharedPreferences(PREFS_NAME_CURRENT, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(KEY_WEATHER_CURRENT, null)
        return gson.fromJson(json, object : TypeToken<WeatherCurrent?>() {}.type)
    }




}
