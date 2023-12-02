package mobappdev.example.apiapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobappdev.example.apiapplication.R
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun DailyView(
    vm: WeatherVM
) {
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.White
    val dividerColor = Color(255, 255, 255, 64)

    val weather = vm.weather.collectAsState()
    val savedWeather by vm.savedWeather.collectAsState()
    val isInternetAvailable = vm.isInternetAvailable()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(secondaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        weather.value?.let { currentWeather ->
            // Display time, temperature, sunrise, and sunset for 7 days in a vertically scrollable way
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = primaryColor,
                        shape = RoundedCornerShape(8.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(currentWeather.daily.time.size) { index ->
                    // Parse the date string into a LocalDate object
                    val dateString = currentWeather.daily.time[index].substring(0, 10)
                    val localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)

                    // Get the name of the weekday
                    val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())



                    // Display day, degrees, sunrise, and sunset in a horizontally scrollable way
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = dayOfWeek,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = textColor
                        )
                        Text(
                            text = "${currentWeather.daily.temperature_2m_min[index].roundToInt()}° / ${currentWeather.daily.temperature_2m_max[index].roundToInt()}°",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = textColor
                        )
                        // Load sunrise icon
                        Image(
                            painter = painterResource(id = R.drawable.sunrise),
                            contentDescription = null,
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )

                        Text(
                            text = "${currentWeather.daily.sunrise[index].substring(11, 16)}",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = textColor,
                            modifier = Modifier.padding(start = 4.dp)
                        )

                        // Load sunset icon
                        Image(
                            painter = painterResource(id = R.drawable.sunset),
                            contentDescription = null,
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                        Text(
                            text = "${currentWeather.daily.sunset[index].substring(11, 16)}",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = textColor
                        )
                    }

                    // Add a Divider to separate rows
                    Divider(
                        color = dividerColor,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.9f) // Set width to 80% of the parent
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}