package mobappdev.example.apiapplication.ui.components;

import android.widget.SearchView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import mobappdev.example.apiapplication.ui.viewmodels.WeatherVM
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
        vm: WeatherVM
) {
        val latitude by vm.latitude.collectAsState()
        val longitude by vm.longitude.collectAsState()
        var latInput by remember { mutableStateOf(latitude.toString()) }
        var lonInput by remember { mutableStateOf(longitude.toString()) }

        Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                OutlinedTextField(
                        value = latitude.toString(),
                        onValueChange = { latInput = it },
                        label = { Text("Latitude") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                        value = lonInput,
                        onValueChange = { lonInput = it },
                        label = { Text("Longitude") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                )

                Button(
                        onClick = {
                                vm.setLatitude(latInput.toDouble())
                                vm.setLongitude(lonInput.toDouble())
                        },
                        modifier = Modifier.fillMaxWidth()
                ) {
                        Text("Search")
                }
        }

}

