package mobappdev.example.apiapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM

@Composable
fun WeatherScreen(
    vm: WeatherVM
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Daily Weather Forecast",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val weather = vm.weather.collectAsState()

        // Check if weather value is not null
        weather.value?.let { currentWeather ->
            // Display time, temperature, sunrise, and sunset in a more visually appealing way
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in currentWeather.daily.time.indices) {
                    Text(
                        text = currentWeather.daily.time[i],
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Max: ${currentWeather.daily.temperature_2m_max[i]}°C",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Min: ${currentWeather.daily.temperature_2m_min[i]}°C",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Extract and display sunrise time
                    val sunriseTime = currentWeather.daily.sunrise[i].substring(11, 16)
                    Text(
                        text = "Sunrise: $sunriseTime",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )

                    // Extract and display sunset time
                    val sunsetTime = currentWeather.daily.sunset[i].substring(11, 16)
                    Text(
                        text = "Sunset: $sunsetTime",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


