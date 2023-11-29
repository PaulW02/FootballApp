package mobappdev.example.apiapplication.data

data class WeatherDetails(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val hourly_units: HourlyUnits,
    val hourly: HourlyData
)

data class WeatherDaily(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val daily: DailyData
)

data class WeatherCurrent(
    val current: CurrentData,
    val daily: DailyData
)

data class CurrentData(
    val time: String,
    val interval: Int,
    val temperature_2m: Double,
    val is_day: Int,
    val rain: Double,
)

data class HourlyUnits(
    val time: String,
    val temperature_2m: String
)

data class DailyData(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>
)

data class HourlyData(
    val time: List<String>,
    val temperature_2m: List<Double>
)
