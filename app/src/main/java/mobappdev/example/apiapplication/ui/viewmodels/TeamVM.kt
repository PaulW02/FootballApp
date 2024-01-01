package mobappdev.example.apiapplication.ui.viewmodels

import PastMatches
import UpcomingMatches
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mobappdev.example.apiapplication.data.TeamsDetails
import mobappdev.example.apiapplication.lookup.LookupTeamClient
import mobappdev.example.apiapplication.lookup.impl.LookupTeamClientImpl
import mobappdev.example.apiapplication.search.MatchesClient
import mobappdev.example.apiapplication.search.impl.MatchesClientImpl
import mobappdev.example.apiapplication.utils.Result


interface TeamViewModel {
    val team: StateFlow<TeamsDetails?>
    fun fetchTeam(id: Int)
    val upcomingMatches: StateFlow<UpcomingMatches?>
    fun fetchUpcomingMatches(id: Int)
    val pastMatches: StateFlow<PastMatches?>

    fun fetchPastMatches(id: Int)

}

class TeamVM (application: Application
) : AndroidViewModel(application), TeamViewModel {
    private val lookupTeamClient: LookupTeamClient = LookupTeamClientImpl()
    private val _team = MutableStateFlow<TeamsDetails?>(null)
    override val team: StateFlow<TeamsDetails?> = _team.asStateFlow()

    private val _teamState = MutableStateFlow<Result<String>>(Result.Loading)
    val teamState: StateFlow<Result<String>> = _teamState

    private val matchesClient: MatchesClient = MatchesClientImpl()
    private val _upcomingMatchState = MutableStateFlow<Result<String>>(Result.Loading)
    val upcomingMatchState: StateFlow<Result<String>> = _upcomingMatchState
    private val _upcomingMatches = MutableStateFlow<UpcomingMatches?>(null)
    override val upcomingMatches: StateFlow<UpcomingMatches?> = _upcomingMatches.asStateFlow()

    private val _pastMatchState = MutableStateFlow<Result<String>>(Result.Loading)
    val pastMatchState: StateFlow<Result<String>> = _pastMatchState
    private val _pastMatches = MutableStateFlow<PastMatches?>(null)
    override val pastMatches: StateFlow<PastMatches?> = _pastMatches.asStateFlow()
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
   override fun fetchUpcomingMatches(id: Int)
    {
        viewModelScope.launch {
            _upcomingMatchState.value = Result.Loading
            try {
                val result = matchesClient.searchUpcomingMatchesById(id)
                Log.e("upcoming mathces ", "upcoming matches $result")
                if (result != null){
                    _upcomingMatches.update { result.getOrNull() }
                }else{
                    _upcomingMatchState.value = Result.Error(Exception("Failed to fetch past matches"))
                }
            }catch (e: Exception)
            {
                _upcomingMatchState.value = Result.Error(e)
            }
        }
    }

    override fun fetchPastMatches(id: Int)
    {
        viewModelScope.launch {
            _pastMatchState.value = Result.Loading
            try {
                val result = matchesClient.searchPastMatchesById(id)
                Log.e("past mathces ", "past matches $result")
                if (result != null){
                    _pastMatches.update { result.getOrNull() }
                }else{
                    _pastMatchState.value = Result.Error(Exception("Failed to fetch past matches"))
                }
            }catch (e: Exception)
            {
                _pastMatchState.value = Result.Error(e)
            }
        }
    }
}
