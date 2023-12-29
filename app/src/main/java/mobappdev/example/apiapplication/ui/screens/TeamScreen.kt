package mobappdev.example.apiapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import mobappdev.example.apiapplication.ui.viewmodels.TeamVM
import mobappdev.example.apiapplication.data.TeamDetails

@Composable
fun TeamScreen(vm: TeamVM, teamId: Int) {
    LaunchedEffect(teamId) {
        vm.fetchTeam(teamId)
    }
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.White
    val teamDetails = vm.team.collectAsState()

    teamDetails.value?.let { team ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor)
        ) {
            items(team.teams.size) { index ->
                // Display team name
                Text(
                    text = team.teams[index].strTeam,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                // Display team badge
                team.teams[index].strTeamBadge.let { badgeUrl ->
                    Image(
                        painter = rememberImagePainter(data = badgeUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(16.dp)
                    )
                }
                TeamDetailsSection(team = team.teams[index])
            }
        }
    }
}

@Composable
fun TeamDetailsSection(team: TeamDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Display other team details here...
        TeamDetailItem(label = "Country", value = team.strCountry)
        TeamDetailItem(label = "Formed Year", value = team.intFormedYear)
        TeamDetailItem(label = "League", value = team.strLeague)
        TeamDetailItem(label = "Stadium", value = team.strStadium)
        // Add more details as needed...
    }
}

@Composable
fun TeamDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}
