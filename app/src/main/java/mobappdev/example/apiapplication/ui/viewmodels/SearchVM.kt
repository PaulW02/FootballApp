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
import mobappdev.example.apiapplication.data.FetchLocation
import mobappdev.example.apiapplication.data.TeamDetails
import mobappdev.example.apiapplication.data.Teams
import mobappdev.example.apiapplication.data.TeamsDetails
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

    private val _locationTeams = MutableStateFlow<TeamsDetails?>(null)
    private val _locatiomTeamState = MutableStateFlow<Result<String>>(Result.Loading)
    val locationTeamState: StateFlow<Result<String>> = _locatiomTeamState
   private val _cityTeams  = MutableStateFlow<List<TeamDetails>?>(null)
    val cityTeams : StateFlow<List<TeamDetails>?> = _cityTeams.asStateFlow()

    private val _teamsState = MutableStateFlow<Result<String>>(Result.Loading)
    val teamsState: StateFlow<Result<String>> = _teamsState

    val locationGetter = FetchLocation(application.applicationContext)


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
        }
    }

    fun fetchTeamByCountry(country:String, ) {
        viewModelScope.launch {
            _locatiomTeamState.value = Result.Loading

            try {
                val result = searchTeamClient.searchTeamByCountry(country)
                Log.e("TEEST ", "TEST $result")
                if (result != null) {

                    _locationTeams.update {
                        result.getOrNull()
                    }


                } else {
                    _locatiomTeamState.value = Result.Error(Exception("Failed to fetch team"))
                }
            } catch (e: Exception) {
                _locatiomTeamState.value = Result.Error(e)
            }
        }
    }
    fun filterTeamsByCity(city: String){
        locationGetter.getLocation()
        Log.e( "CITYYYYYYY",  "cityyyy ${locationGetter.city}")
        Log.e( "TAG",  "cityyyy ${locationGetter.city}")
        Log.e( "TAG",  "cityyyy ${locationGetter.city}")
        Log.e( "TAG",  "cityyyy ${locationGetter.city}")
        Log.e( "TAG",  "cityyyy ${locationGetter.city}")

        _cityTeams.value =  _locationTeams.value?.teams?.filter { team ->
            team.strStadiumLocation.equals(city, ignoreCase = true)
        }
    }


}
