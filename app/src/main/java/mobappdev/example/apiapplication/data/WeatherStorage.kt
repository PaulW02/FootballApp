package mobappdev.example.apiapplication.data
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import mobappdev.example.apiapplication.utils.localDateToString
import java.time.LocalDate

object WeatherStorage {
    private const val PREFS_NAME = "JokePrefs"
    private const val KEY_JOKE = "saved_joke"
    private const val KEY_LASTUPDATE = "last_update"

    fun saveWeather(context: Context, weather: WeatherDaily, date: LocalDate) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(KEY_JOKE, weather.timezone)
            putString(KEY_LASTUPDATE, localDateToString(date))
        }
    }

    fun getSavedWeather(context: Context): Array<String?> {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return arrayOf(
            prefs.getString(KEY_JOKE, null),
            prefs.getString(KEY_LASTUPDATE, null)
        )
    }
}
