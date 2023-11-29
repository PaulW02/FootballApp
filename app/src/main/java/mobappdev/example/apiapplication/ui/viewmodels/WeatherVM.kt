package mobappdev.example.apiapplication.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mobappdev.example.apiapplication.data.WeatherDaily
import mobappdev.example.apiapplication.data.WeatherDetails
import mobappdev.example.apiapplication.data.WeatherStorage
import mobappdev.example.apiapplication.networking.WeatherDataSource
import mobappdev.example.apiapplication.utils.Result
import mobappdev.example.apiapplication.utils.localDateToString
import java.time.LocalDate


interface WeatherViewModel {
    val weather: StateFlow<WeatherDaily?>
    val weatherToday: StateFlow<WeatherDetails?>

    fun fetchWeather()
    fun fetchWeatherToday()
}

class WeatherVM(
    application: Application
) : AndroidViewModel(application), WeatherViewModel {

    private val _weather = MutableStateFlow<WeatherDaily?>(null)
    override val weather: StateFlow<WeatherDaily?> = _weather.asStateFlow()

    private val _weatherState = MutableStateFlow<Result<String>>(Result.Loading)
    val weatherState: StateFlow<Result<String>> = _weatherState

    private val _weatherToday = MutableStateFlow<WeatherDetails?>(null)
    override val weatherToday: StateFlow<WeatherDetails?> = _weatherToday.asStateFlow()

    private val _weatherTodayState = MutableStateFlow<Result<String>>(Result.Loading)
    val weatherTodayState: StateFlow<Result<String>> = _weatherTodayState

    private val _latitude = MutableStateFlow<Double>(59.34)
      val latitude: StateFlow<Double>
         get() = _latitude

    private val _longitude = MutableStateFlow<Double>(18.0687)
     val longitude: StateFlow<Double>
        get() = _longitude

    init {
        fetchWeather()
         fetchWeatherToday()
        getSavedWeather()
    }

    fun setLongitude(longitude: Double) {
        _longitude.value = longitude
    }
    fun setLatitude(latitude: Double)
    {
        _latitude.value = latitude
    }

    override fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = Result.Loading
            try {
                val result = WeatherDataSource.getWeatherForWeek(latitude.value, longitude.value)
                if (result is Result.Success) {
                    _weather.update { result.data }
                    // Save weather
                    WeatherStorage.saveWeather(getApplication<Application>().applicationContext, result.data, LocalDate.now())
                    _weatherState.value = Result.Success(result.data.timezone)
                } else {
                    _weatherState.value = Result.Error(Exception("Failed to fetch weather"))
                }
            } catch (e: Exception) {
                _weatherState.value = Result.Error(e)
            }
        }
    }

    override fun fetchWeatherToday() {
        viewModelScope.launch {
            _weatherTodayState.value = Result.Loading
            try {
                val result = WeatherDataSource.getStockholmTodayWeather()
                if (result is Result.Success) {
                    _weatherToday.update { result.data }
                    // Save weather
                    //WeatherStorage.saveWeather(getApplication<Application>().applicationContext, result.data, LocalDate.now())
                    _weatherTodayState.value = Result.Success(result.data.timezone)
                } else {
                    _weatherTodayState.value = Result.Error(Exception("Failed to fetch weather"))
                }
            } catch (e: Exception) {
                _weatherTodayState.value = Result.Error(e)
            }
        }
    }
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    private fun getSavedWeather() {
        val storedWeather = WeatherStorage.getSavedWeather(getApplication<Application>().applicationContext)
        if (storedWeather[1] != localDateToString(LocalDate.now()) || (storedWeather[0] ?: "") == "") {
            fetchWeather()
        } else {
            _weather.update { null } // Clear previous weather data
            _weatherState.value = Result.Success("Weather loaded from cache") // Indicate that weather was loaded from cache
        }
    }
}
