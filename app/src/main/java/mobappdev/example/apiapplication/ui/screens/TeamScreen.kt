package mobappdev.example.apiapplication.ui.screens

import PastMatch
import PastMatches
import UpcomingMatch
import UpcomingMatches
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import mobappdev.example.apiapplication.ui.viewmodels.TeamVM
import mobappdev.example.apiapplication.data.TeamDetails

@Composable
fun TeamScreen(vm: TeamVM, teamId: Int) {
    LaunchedEffect(teamId) {
        vm.fetchTeam(teamId)
        vm.fetchPastMatches(teamId)
        vm.fetchUpcomingMatches(teamId)
    }
    val primaryColor = Color(0xFF1976D2)
    val textColor = Color.White
    val teamDetails = vm.team.collectAsState()
    val upcomingMatches = vm.upcomingMatches.collectAsState()
    val pastMatches = vm.pastMatches.collectAsState()
    val teamBadge = teamDetails.value?.teams?.get(0)?.strTeamBadge
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
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(0.2f)
            .fillMaxHeight()
            .background(primaryColor)
        )  {
            pastMatches.value?.let { past ->
                if (past.results.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        PastMatchesScreen(pastMatches = past, teamId = teamId, teamBadge = teamBadge.toString())
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

        /*Column(modifier = Modifier
            .fillMaxSize()
            .weight(0.2f)
            .fillMaxHeight()
            .background(primaryColor)
        ) {
            upcomingMatches.value?.let { upcoming ->
                if (upcoming.events.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        UpcomingMatchesScreen(
                            upcomingMatches = upcoming,
                            teamBadge = teamBadge.toString()
                        )
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
        }*/
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
fun PastMatchItem(match: PastMatch, teamBadge: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {},
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display home team badge as an image
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                BadgeImage(match.strHomeTeamBadge, teamBadge)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = match.intHomeScore.toString(),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "-",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = match.intAwayScore.toString(),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BadgeImage(match.strAwayTeamBadge, teamBadge)
            }

            // Display individual match details
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${match.strHomeTeam} vs ${match.strAwayTeam}")
                    }
                },
                onClick = { offset ->
                    // Handle click on team names if needed
                },
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            //MatchDetailItem(Icons.Default.Place, match.strVenue)
            //MatchDetailItem(Icons.Default.DateRange, match.dateEvent)
            //MatchDetailItem(Icons.Default.Info, match.strTime)
        }
    }
}

@Composable
fun UpcomingMatchItem(match: UpcomingMatch, teamBadge: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {},
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display home team badge as an image
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                BadgeImage(match.strHomeTeamBadge, teamBadge)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "-",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BadgeImage(match.strAwayTeamBadge, teamBadge)
            }

            // Display individual match details
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${match.strHomeTeam} vs ${match.strAwayTeam}")
                    }
                },
                onClick = { offset ->
                    // Handle click on team names if needed
                },
                //modifier = Modifier.padding(8.dp)
            )
            //Spacer(modifier = Modifier.height(8.dp))

            //MatchDetailItem(Icons.Default.Place, match.strVenue)
            //MatchDetailItem(Icons.Default.DateRange, match.dateEvent)
            //MatchDetailItem(Icons.Default.Info, match.strTime)
        }
    }
}

@Composable
fun BadgeImage(badge: String, teamBadge: String) {
    Image(
        painter = if (badge == "BadgeURL") {
            rememberImagePainter(data = teamBadge)
        } else {
            rememberImagePainter(data = badge)
        },
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp),
        contentScale = ContentScale.Crop
    )
}


@Composable
fun MatchDetailItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.Gray,
            modifier = Modifier.padding(4.dp)
        )
    }
}
@Composable
fun UpcomingMatchesScreen(upcomingMatches: UpcomingMatches, teamBadge: String) {
    upcomingMatches?.let { matches ->
        LazyColumn {
            items(matches.events) { match ->
                UpcomingMatchItem(match = match, teamBadge = teamBadge)
            }
        }
    } ?: Text(text = "No upcoming matches available")
}


fun getMatchResultColor(match: PastMatch, teamId: Int): Color {
    return when {
        match.intHomeScore > match.intAwayScore && match.idHomeTeam == teamId -> Color.Green
        match.intAwayScore > match.intHomeScore && match.idAwayTeam == teamId -> Color.Green
        match.intHomeScore == match.intAwayScore -> Color.Gray
        else -> Color.Red
    }
}

fun getMatchResultIcon(match: PastMatch, teamId: Int): String {
    return when {
        match.intHomeScore > match.intAwayScore && match.idHomeTeam == teamId -> "V"
        match.intAwayScore > match.intHomeScore && match.idAwayTeam == teamId -> "V"
        match.intHomeScore == match.intAwayScore -> "O"
        else -> "F"
    }
}



@Composable
fun MatchResultCircle(index: Int, match: PastMatch, teamId: Int, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(getMatchResultColor(match, teamId))
            .clickable {
                onClick(index) // Pass the index of the clicked match
            }
    ) {
        // Display V, O, or F based on match result
        Text(
            text = getMatchResultIcon(match, teamId),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PastMatchesScreen(pastMatches: PastMatches, teamId: Int, teamBadge: String) {
    val scope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = selectedIndex,
        initialPageOffsetFraction = 0f
    ) {
        pastMatches.results.size
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = true
        ) { page ->
            PastMatchItem(match = pastMatches.results[page], teamBadge = teamBadge)
        }
        // Display circles for each past match
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            reverseLayout = true,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            items(pastMatches.results.size) { index ->
                MatchResultCircle(
                    index = index,
                    match = pastMatches.results[index],
                    teamId = teamId,
                    onClick = { clickedIndex ->
                        // Scroll to the selected match when a circle is clicked
                        scope.launch {
                            pagerState.animateScrollToPage(clickedIndex)
                        }
                    }
                )
            }
        }

        // Display past matches using HorizontalPager

    }
}
