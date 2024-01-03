package mobappdev.example.apiapplication.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mobappdev.example.apiapplication.data.Teams
import mobappdev.example.apiapplication.search.SearchTeamClient
import mobappdev.example.apiapplication.search.impl.SearchTeamClientImpl
import mobappdev.example.apiapplication.utils.Result


interface SearchViewModel {
    val teams: StateFlow<Teams?>
    fun fetchTeams(name: String)
}

class SearchVM (application: Application
) : AndroidViewModel(application), SearchViewModel {
    private val searchTeamClient: SearchTeamClient = SearchTeamClientImpl()
    private val _teams = MutableStateFlow<Teams?>(null)
    override val teams: StateFlow<Teams?> = _teams.asStateFlow()

    private val _teamsState = MutableStateFlow<Result<String>>(Result.Loading)
    val teamsState: StateFlow<Result<String>> = _teamsState
    override fun fetchTeams(name: String) {
        viewModelScope.launch {
            _teamsState.value = Result.Loading

            try {
                val result = searchTeamClient.byName(name)
                Log.e("TEEST ", "TEST $result")
                if (result != null) {
                    _teams.update { result.getOrNull() }
                    // Save weather
                    //_leagueState.value = Result.Success(result.data.timezone)
                    //WeatherStorage.saveWeather(getApplication<Application>().applicationContext,result.data)
                } else {
                    _teamsState.value = Result.Error(Exception("Failed to fetch teams"))
                }
            } catch (e: Exception) {
                _teamsState.value = Result.Error(e)
            }
        }    }
}
