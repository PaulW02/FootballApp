package mobappdev.example.apiapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(secondaryColor)
        ) {
            HourlyView(vm = vm)
            Spacer(modifier = Modifier.height(16.dp))
            DailyView(vm = vm)
            Spacer(modifier = Modifier.height(2.dp))
            SearchView(vm = vm)
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
