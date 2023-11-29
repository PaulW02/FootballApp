package mobappdev.example.apiapplication.ui.components

import android.widget.SearchView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(vm: WeatherVM) {
        val latitude by vm.latitude.collectAsState()
        val longitude by vm.longitude.collectAsState()
        var latInput by remember { mutableStateOf(latitude.toString()) }
        var lonInput by remember { mutableStateOf(longitude.toString()) }

        val inputTextStyle =
                androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 16.sp)
        val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                placeholderColor = Color.White.copy(alpha = 0.7f),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                cursorColor = Color.White
        )

        Row(
                modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                OutlinedTextField(
                        value = latInput,
                        onValueChange = { latInput = it },
                        label = { Text("Latitude", style = inputTextStyle) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = outlinedTextFieldColors,
                        modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                        value = lonInput,
                        onValueChange = { lonInput = it },
                        label = { Text("Longitude", style = inputTextStyle) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = outlinedTextFieldColors,
                        modifier = Modifier.weight(1f)
                )

                Button(
                        onClick = {
                                vm.setLatitude(latInput.toDouble())
                                vm.setLongitude(lonInput.toDouble())
                                vm.fetchWeather()
                        },
                        modifier = Modifier.weight(1f)
                ) {
                        Text("Search", color = Color.White, fontSize = 16.sp)
                }
        }
}

