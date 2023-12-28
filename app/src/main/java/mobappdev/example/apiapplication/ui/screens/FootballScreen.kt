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
import mobappdev.example.apiapplication.ui.viewmodels.LeagueVM
@Composable
fun FootballScreen(
    vm: LeagueVM
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
                    Spacer(modifier = Modifier.height(16.dp))
                    LeaguesView(vm = vm)
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        } else {
            LeaguesView(vm = vm)
        }
    }
}
