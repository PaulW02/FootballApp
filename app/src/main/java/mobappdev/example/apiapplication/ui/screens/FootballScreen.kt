import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import mobappdev.example.apiapplication.ui.viewmodels.LeagueVM
@Composable
fun FootballScreen(
    vm: LeagueVM,
    navController: NavHostController
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
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(secondaryColor)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.05f)
                            .fillMaxHeight()
                    ) {
                        FootballHeader(navController = navController)
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Home page")
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
                //LeaguesView(vm = vm)
            }
            //LeaguesView(vm = vm)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootballHeader(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Home",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("search")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

            }
        }
    )
}
