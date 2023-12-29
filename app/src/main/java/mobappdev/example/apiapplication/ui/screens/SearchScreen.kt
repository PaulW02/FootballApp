import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.flow.StateFlow
import mobappdev.example.apiapplication.data.TeamShort
import mobappdev.example.apiapplication.data.Teams
import mobappdev.example.apiapplication.ui.screens.TeamScreen
import mobappdev.example.apiapplication.ui.viewmodels.SearchVM

@Composable
fun SearchScreen(vm: SearchVM, navController: NavController) {
    // Define color palette
    val primaryColor = Color(0xFF1976D2)
    val secondaryColor = Color(0xFF90CAF9)
    val textColor = Color.Black

    val teams = vm.teams.collectAsState()

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
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        } else {
            // Portrait orientation
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(secondaryColor)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                SearchForm(vm)
                Spacer(modifier = Modifier.height(16.dp))
                // Display search results or other content below the form
                teams.value?.let { teams ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = primaryColor,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(teams.teams.size) { index ->
                            // Display teams information in a vertically scrollable way
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(primaryColor)
                                        .clickable {
                                            // Handle click on the team
                                            navController.navigate("team/${teams.teams[index].idTeam}")
                                        },
                                    verticalAlignment = Alignment.Bottom,
                                ) {
                                    Image(
                                        painter = rememberImagePainter(data = teams.teams[index].strTeamBadge),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(50.dp) // Adjust the size as needed
                                            .padding(vertical = 8.dp)
                                    )

                                    Text(
                                        text = teams.teams[index].strTeam,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        ),
                                        color = textColor
                                    )
                                }
                                // Add a Divider to separate teams entries
                                Divider(
                                    color = Color.Gray,
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
        }
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
