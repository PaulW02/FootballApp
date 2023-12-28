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
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM

@Composable
fun LeaguesScreen(
    vm: WeatherVM
) {

    // Define color palette
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.Black

    // Apply color sche
        //val configuration = LocalConfiguration.current
        //val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            // Landscape orientation
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(secondaryColor)
    ) {

        // SearchView taking 20% of the screen width
        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
