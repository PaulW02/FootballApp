package mobappdev.example.apiapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mobappdev.example.apiapplication.data.WeatherDaily
import mobappdev.example.apiapplication.data.WeatherStorage
import mobappdev.example.apiapplication.networking.WeatherDataSource
import mobappdev.example.apiapplication.utils.Result
import mobappdev.example.apiapplication.utils.localDateToString
import java.time.LocalDate


interface WeatherViewModel {
    val weather: StateFlow<WeatherDaily?>

    fun fetchWeather()
}

class WeatherVM(
    application: Application
) : AndroidViewModel(application), WeatherViewModel {

    private val _weather = MutableStateFlow<WeatherDaily?>(null)
    override val weather: StateFlow<WeatherDaily?> = _weather.asStateFlow()

    private val _weatherState = MutableStateFlow<Result<String>>(Result.Loading)
    val weatherState: StateFlow<Result<String>> = _weatherState

    init {
        fetchWeather()
        getSavedWeather()
    }

    override fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = Result.Loading
            try {
                val result = WeatherDataSource.getStockholmDailyWeather()
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
