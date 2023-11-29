import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM
import kotlin.math.roundToInt

@Composable
fun Overview(
    vm: WeatherVM
) {
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.White

    val weatherOverview = vm.weatherCurrent.collectAsState()
    weatherOverview.value?.let { currentWeather ->
        // Display current weather overview
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.percentOfMaxHeight().dp) // Adjust the percentage as needed
                .background(secondaryColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Current temperature
            Text(
                text = "${currentWeather.current.temperature_2m.roundToInt()}°",
                style = typography.displayLarge.copy(fontWeight = FontWeight.Bold), // Adjust text size as needed
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "H: ${currentWeather.daily.temperature_2m_max[0].roundToInt()}°",
                    style = typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    modifier = Modifier.padding(top = 8.dp, end = 8.dp)
                )
                Text(
                    text = "L: ${currentWeather.daily.temperature_2m_min[0].roundToInt()}°",
                    style = typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                )
            }

/*            // Current weather icon
            val weatherIcon = remember(currentWeather.icon) {
                weatherIcons[currentWeather.icon] ?: Icons.Default.WeatherCloudy
            }

            Icon(
                imageVector = weatherIcon,
                contentDescription = null, // Provide a meaningful description
                tint = textColor,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clip(CircleShape)
                    .background(surfaceColor)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // Other weather information
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherInfo(icon = Icons.Default.TemperatureCelsius, text = "${currentWeather.apparentTemperature.roundToInt()}°")
                WeatherInfo(icon = Icons.Default.Water, text = "${currentWeather.humidity.roundToInt()}%")
                WeatherInfo(icon = Icons.Default.Opacity, text = "${currentWeather.cloudCover.roundToInt()}%")
                WeatherInfo(icon = Icons.Default.WbSunny, text = "${currentWeather.uvIndex}")
                WeatherInfo(icon = Icons.Default.Time, text = currentWeather.time)
                WeatherInfo(icon = Icons.Default.WbTwilight, text = currentWeather.sunrise)
                WeatherInfo(icon = Icons.Default.WbTwilightSmall, text = currentWeather.sunset)
                WeatherInfo(icon = Icons.Default.WeatherShowers, text = "${currentWeather.precipIntensity} mm")
                WeatherInfo(icon = Icons.Default.WeatherThunderstorm, text = "${currentWeather.windSpeed} m/s")
            }*/
        }
    }
}

/*@Composable
fun WeatherInfo(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null, // Provide a meaningful description
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .size(24.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}*/

@Composable
fun Int.percentOfMaxHeight(): Int {
    return (LocalConfiguration.current.screenHeightDp * this / 100)
}