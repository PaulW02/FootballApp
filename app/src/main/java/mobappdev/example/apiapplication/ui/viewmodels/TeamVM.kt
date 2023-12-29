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
import mobappdev.example.apiapplication.data.League
import mobappdev.example.apiapplication.data.TeamDetails
import mobappdev.example.apiapplication.data.Teams
import mobappdev.example.apiapplication.data.TeamsDetails
import mobappdev.example.apiapplication.data.WeatherDaily
import mobappdev.example.apiapplication.data.WeatherStorage
import mobappdev.example.apiapplication.list.ListTeamsClient
import mobappdev.example.apiapplication.list.impl.ListTeamsClientImpl
import mobappdev.example.apiapplication.lookup.LookupTeamClient
import mobappdev.example.apiapplication.lookup.impl.LookupTeamClientImpl
import mobappdev.example.apiapplication.networking.WeatherDataSource
import mobappdev.example.apiapplication.utils.Result


interface TeamViewModel {
    val team: StateFlow<TeamsDetails?>
    fun fetchTeam(id: Int)
}

class TeamVM (application: Application
) : AndroidViewModel(application), TeamViewModel {
    private val lookupTeamClient: LookupTeamClient = LookupTeamClientImpl()
    private val _team = MutableStateFlow<TeamsDetails?>(null)
    override val team: StateFlow<TeamsDetails?> = _team.asStateFlow()

    private val _teamState = MutableStateFlow<Result<String>>(Result.Loading)
    val teamState: StateFlow<Result<String>> = _teamState
    override fun fetchTeam(id: Int) {
        viewModelScope.launch {
            _teamState.value = Result.Loading

            try {
                val result = lookupTeamClient.byId(id)
                Log.e("TEEST ", "TEST $result")
                if (result != null) {
                    _team.update { result.getOrNull() }
                    // Save weather
                    //_leagueState.value = Result.Success(result.data.timezone)
                    //WeatherStorage.saveWeather(getApplication<Application>().applicationContext,result.data)
                } else {
                    _teamState.value = Result.Error(Exception("Failed to fetch team"))
                }
            } catch (e: Exception) {
                _teamState.value = Result.Error(e)
            }
        }
    }
}
