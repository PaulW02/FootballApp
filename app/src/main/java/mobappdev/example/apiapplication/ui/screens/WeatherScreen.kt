import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import mobappdev.example.apiapplication.ui.components.DailyView
import mobappdev.example.apiapplication.ui.components.HourlyView
import mobappdev.example.apiapplication.ui.components.SearchView
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM

@Composable
fun WeatherScreen(
    vm: WeatherVM
) {
    // Define color palette
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.Black

    // Apply color scheme
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = primaryColor,
            secondary = secondaryColor,
            onPrimary = textColor,
            onSecondary = textColor
        )
    ) {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (isLandscape) {
            // Landscape orientation
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(secondaryColor)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                ) {
                    HourlyView(vm = vm)
                    Spacer(modifier = Modifier.height(16.dp))
                    DailyView(vm = vm)
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // SearchView taking 20% of the screen width
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                ) {
                    Overview(vm = vm)
                    Spacer(modifier = Modifier.height(16.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    SearchView(vm = vm)
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        } else {
            // Portrait orientation
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(secondaryColor)
            ) {
                // Content taking 80% of the screen height
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Overview(vm = vm)
                    Spacer(modifier = Modifier.height(16.dp))
                    HourlyView(vm = vm)
                    Spacer(modifier = Modifier.height(16.dp))
                    DailyView(vm = vm)
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // SearchView taking 20% of the screen height
                Column(
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(2.dp))
                    SearchView(vm = vm)
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}
