package mobappdev.example.apiapplication

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mobappdev.example.apiapplication.ui.screens.WeatherScreen
import mobappdev.example.apiapplication.ui.theme.JokeGeneratorTheme
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM

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
                    val jokeViewModel = WeatherVM(application = application)
                    val isInternetAvailable = jokeViewModel.isInternetAvailable(applicationContext)
                    if (!isInternetAvailable) {
                        Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT).show()
                    } else {
                        WeatherScreen(vm = jokeViewModel)
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