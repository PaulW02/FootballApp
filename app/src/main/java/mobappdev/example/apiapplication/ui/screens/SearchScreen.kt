import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mobappdev.example.apiapplication.data.Teams
import mobappdev.example.apiapplication.ui.viewmodels.SearchVM
import mobappdev.example.apiapplication.data.TeamDetails


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SearchScreen(vm: SearchVM, navController: NavController) {


    // Define color palette
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.Black

    val teams = vm.teams.collectAsState()
    val locationTeams = vm.cityTeams.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Teams", "Locations")
    LaunchedEffect(Unit) {
        vm.fetchTeamByCountry("Sweden")
        vm.filterTeamsByCity("Södertälje")
    }
    // Apply color scheme
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = primaryColor,
            secondary = secondaryColor,
            onPrimary = textColor,
            onSecondary = textColor
        )
    ) {
        Scaffold(
            topBar = {
                // Add tabs to select between Teams and Locations
                NavigationBar(
                    contentColor = textColor
                ) {
                    tabs.forEachIndexed { index, title ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = {
                                selectedTab = index
                                      vm.locationGetter.getLocation() },
                            icon =  {Icons.Default.Search},
                            label = { Text(text = title) }
                        )
                    }
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .padding(16.dp) // Apply content padding here
                        .fillMaxSize()
                ) {
                    when (selectedTab) {
                        0 -> DisplayTeams(teams.value, navController,vm)
                        1 -> DisplayLocationTeams(locationTeams.value,navController)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchForm(vm: SearchVM) {
    var query by remember { mutableStateOf(TextFieldValue()) }

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = {
                Text(text = "Enter your search query")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                vm.fetchTeams(query.text)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Search")
        }
    }
}
@Composable
fun DisplayTeams(teams: Teams?, navController: NavController,vm: SearchVM) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Search Form
        SearchForm(vm)

        Text(
            text = "Teams",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display teams information
        teams?.teams?.let { teamsList ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(teamsList.size) { index ->
                    // Display teams information in a vertically scrollable way
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                // Handle click on the team
                                navController.navigate("team/${teamsList[index].idTeam}")
                            }
                    ) {
                        Text(
                            text = teamsList[index].strTeam,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = Color.Black
                        )
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayLocationTeams(locationTeams: List<TeamDetails>?,navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Location-based Teams",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Display location-based teams information
        locationTeams?.let { teams ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(teams.size) { index ->
                    // Display teams information in a vertically scrollable way
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                // Handle click on the team

                                // Handle click on the team
                                navController.navigate("team/${locationTeams[index].idTeam}")
                            }
                    ) {
                        Text(
                            text = teams[index].strTeam,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = Color.Black
                        )
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}