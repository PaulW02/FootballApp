package mobappdev.example.apiapplication.ui.screens

import UpcomingMatch
import UpcomingMatches
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
        vm.fetchUpcomingMatches(teamId)
    }
    val primaryColor = Color(0xFF1976D2)
    val textColor = Color.White
    val teamDetails = vm.team.collectAsState()
    val upcomingMatches = vm.upcomingMatches.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
    ) {
        teamDetails.value?.let { team ->
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
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

        upcomingMatches.value?.let { upcoming ->
            if (upcoming.events.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    UpcomingMatchesScreen(upcomingMatches = upcoming)
                }
            } else {
                Text(
                    text = "No upcoming matches available",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = textColor
                )
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
@Composable
fun UpcomingMatchItem(match: UpcomingMatch) {
    // Display individual match details here
    Text(
        text = "${match.strHomeTeam} vs ${match.strAwayTeam}",
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = "Venue: ${match.strVenue}",
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = "Date: ${match.dateEvent}",
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
    Text(
        text = "Time: ${match.strTime}",
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
    Divider(color = Color.Gray, thickness = 2.dp)
}
@Composable
fun UpcomingMatchesScreen(upcomingMatches: UpcomingMatches?) {
    upcomingMatches?.let { matches ->
        LazyColumn {
            items(matches.events) { match ->
                UpcomingMatchItem(match = match)
            }
        }
    } ?: Text(text = "No upcoming matches available")
}
