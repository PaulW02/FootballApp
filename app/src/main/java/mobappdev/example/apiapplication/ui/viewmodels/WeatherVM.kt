package mobappdev.example.apiapplication.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mobappdev.example.apiapplication.data.WeatherCurrent
import mobappdev.example.apiapplication.data.WeatherDaily
import mobappdev.example.apiapplication.data.WeatherDetails
import mobappdev.example.apiapplication.data.WeatherStorage
import mobappdev.example.apiapplication.networking.WeatherDataSource
import mobappdev.example.apiapplication.utils.Result
import java.time.LocalDate


interface WeatherViewModel {
    val weather: StateFlow<WeatherDaily?>
    val weatherToday: StateFlow<WeatherDetails?>
    val weatherCurrent: StateFlow<WeatherCurrent?>



    fun fetchWeather()
    fun fetchWeatherToday()
    fun fetchWeatherCurrent()
}

class WeatherVM(
    application: Application
) : AndroidViewModel(application), WeatherViewModel {

    private val _weather = MutableStateFlow<WeatherDaily?>(null)
    override val weather: StateFlow<WeatherDaily?> = _weather.asStateFlow()

    private val _weatherState = MutableStateFlow<Result<String>>(Result.Loading)
    val weatherState: StateFlow<Result<String>> = _weatherState


    private val _savedWeather = MutableStateFlow<WeatherDaily?>(null)
    val savedWeather: StateFlow<WeatherDaily?> = _savedWeather.asStateFlow()

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

    private val _weatherCurrent = MutableStateFlow<WeatherCurrent?>(null)
    override val weatherCurrent: StateFlow<WeatherCurrent?> = _weatherCurrent.asStateFlow()

    private val _weatherCurrentState = MutableStateFlow<Result<String>>(Result.Loading)
    val weatherCurrentState: StateFlow<Result<String>> = _weatherCurrentState


    init {
       loadWeatherData()
    }

    private fun loadWeatherData() {
        if (isInternetAvailable()) {
            fetchWeather()
            fetchWeatherToday()
            fetchWeatherCurrent()
        } else {
            Toast.makeText(getApplication<Application>().applicationContext, "No internet connection", Toast.LENGTH_SHORT).show()
            loadWeatherLocally()
        }
    }


    private fun loadWeatherLocally() {
        val savedWeather = WeatherStorage.loadWeather(getApplication<Application>().applicationContext)
        setWeather(savedWeather) // Update LiveData or StateFlow with locally saved data
        val savedWeatherDetails = WeatherStorage.loadWeatherDetails(getApplication<Application>().applicationContext)
        setWeatherDetails(savedWeatherDetails)
        val savedWeatherCurrent = WeatherStorage.loadWeatherCurrent(getApplication<Application>().applicationContext)
        setWeatherCurrent(savedWeatherCurrent)

    }
    fun setLongitude(longitude: Double) {
        _longitude.value = longitude
    }
    fun setLatitude(latitude: Double)
    {
        _latitude.value = latitude
    }
   private fun setWeather(weatherData: WeatherDaily?) {
        _weather.value = weatherData
    }
  private  fun setWeatherDetails(weatherData: WeatherDetails?) {
        _weatherToday.value = weatherData
    }
    private fun setWeatherCurrent(weatherCurrent: WeatherCurrent?)
    {
        _weatherCurrent.value = weatherCurrent
    }
    override fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = Result.Loading

            try {
                val result = WeatherDataSource.getWeatherForWeek(latitude.value, longitude.value)
                if (result is Result.Success) {
                    _weather.update { result.data }
                    // Save weather
                    _weatherState.value = Result.Success(result.data.timezone)
                    WeatherStorage.saveWeather(getApplication<Application>().applicationContext,result.data)
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
                val result = WeatherDataSource.getStockholmTodayWeather(latitude.value, longitude.value)
                if (result is Result.Success) {
                    _weatherToday.update { result.data }
                    // Save weather
                    WeatherStorage.saveWeatherDetails(getApplication<Application>().applicationContext,result.data)
                    _weatherTodayState.value = Result.Success(result.data.timezone)
                } else {
                    _weatherTodayState.value = Result.Error(Exception("Failed to fetch weather"))
                }
            } catch (e: Exception) {
                _weatherTodayState.value = Result.Error(e)
            }
        }
    }

    override fun fetchWeatherCurrent() {
        viewModelScope.launch {
            _weatherCurrentState.value = Result.Loading
            try {
                val result = WeatherDataSource.getCurrentWeather(latitude.value, longitude.value)
                if (result is Result.Success) {
                    _weatherCurrent.update { result.data }
                    WeatherStorage.saveWeatherCurrent(getApplication<Application>().applicationContext,result.data)
                    _weatherCurrentState.value = Result.Success(result.data.current.time)
                } else {
                    _weatherCurrentState.value = Result.Error(Exception("Failed to fetch weather"))
                }
            } catch (e: Exception) {
                _weatherCurrentState.value = Result.Error(e)
            }
        }
    }
    fun isInternetAvailable(): Boolean {
        val connectivityManager = getApplication<Application>().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }





}


