package mobappdev.example.apiapplication

import FootballScreen
import SearchScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mobappdev.example.apiapplication.ui.screens.TeamScreen
import mobappdev.example.apiapplication.ui.theme.JokeGeneratorTheme
import mobappdev.example.apiapplication.ui.viewmodels.LeagueVM
import mobappdev.example.apiapplication.ui.viewmodels.MatchVM
import mobappdev.example.apiapplication.ui.viewmodels.SearchVM
import mobappdev.example.apiapplication.ui.viewmodels.TeamVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokeGeneratorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val leaguesVM = LeagueVM(application = application)
                    val searchVM = SearchVM(application = application)
                    val teamVM = TeamVM(application = application)
                    val matchVM = MatchVM(application=application)

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            FootballScreen(vm = leaguesVM, navController = navController)
                        }
                        composable("search") {
                            // Replace with the content of your search screen
                            SearchScreen(vm = searchVM, navController = navController)
                        }
                        composable(
                            route = "team/{teamId}",
                            arguments = listOf(navArgument("teamId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val teamId = backStackEntry.arguments?.getString("teamId")
                            if (teamId != null) {
                                TeamScreen(vm = teamVM, teamId = teamId.toInt())
                            } else {
                                // Handle the case where teamId is null
                                Text("Error: Invalid teamId")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JokeGeneratorTheme {
        Greeting("Android")
    }
}

