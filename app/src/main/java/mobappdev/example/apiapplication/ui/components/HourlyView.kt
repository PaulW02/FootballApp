package mobappdev.example.apiapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import kotlin.math.roundToInt


@Composable
fun HourlyView(
    vm: WeatherVM
) {
    // Define color palette
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.Black
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Add padding to create space between the outer border and the contents
    ) {

        Text(
            text = "Hourly Temperature Forecast Today",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val weatherToday = vm.weatherToday.collectAsState()
        weatherToday.value?.let { currentWeather ->
            // Display hourly temperature for today in a horizontally scrollable way
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = primaryColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp)
            ) {
                items(currentWeather.hourly.time.size) { index ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(16.dp) // Increase padding to make the borders larger
                    ) {
                        // Extract the hour part from the timestamp (assuming the format "yyyy-MM-dd'T'HH:mm")
                        val hour = currentWeather.hourly.time[index].substring(11, 13)

                        // Load the weather icon from the drawable resource
                        val iconRes = remember { R.drawable.cloud }

                        // Display the weather icon

                        Text(
                            text = hour,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = null, // Provide a meaningful description
                            modifier = Modifier
                                .height(24.dp) // Adjust the height as needed
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${currentWeather.hourly.temperature_2m[index].roundToInt()}°",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}