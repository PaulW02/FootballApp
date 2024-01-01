package mobappdev.example.apiapplication.ui.viewmodels

import Matches
import UpcomingMatches
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

import mobappdev.example.apiapplication.search.impl.SearchEventClientImpl
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mobappdev.example.apiapplication.search.impl.MatchesClientImpl
import mobappdev.example.apiapplication.utils.Result

interface MatchViewModel{

}
class MatchVM(
application: Application

): AndroidViewModel(application) {
    private val searchEventClient: SearchEventClientImpl = SearchEventClientImpl()
    private val _matchState = MutableStateFlow<Result<String>>(Result.Loading)
    val matchState: StateFlow<Result<String>> = _matchState
    private val _matches = MutableStateFlow<Matches?>(null)
    val matches: StateFlow<Matches?> = _matches.asStateFlow()

    private val upcomingMatchesClientImpl: MatchesClientImpl = MatchesClientImpl()
    private val _upcomingMatchState = MutableStateFlow<Result<String>>(Result.Loading)
    private val _upcomingMatches = MutableStateFlow<UpcomingMatches?>(null)
    val upcomingMatches: StateFlow<UpcomingMatches?> = _upcomingMatches.asStateFlow()

    init {
        fetchaMatch()
        fetchUpcomingMatches(133602)
    }
    fun fetchUpcomingMatches(id: Int)
    {
        viewModelScope.launch {
            _upcomingMatchState.value = Result.Loading
            try {
                val result = upcomingMatchesClientImpl.searchUpcomingMatchesById(id)
                Log.e("upcoming mathces ", "upcoming matches $result")
                if (result != null){
                    _upcomingMatches.update { result.getOrNull() }
                }else{
                    _upcomingMatchState.value = Result.Error(Exception("Failed to fetch weather"))
                }
            }catch (e: Exception)
            {
                _upcomingMatchState.value = Result.Error(e)
            }
        }
    }
    fun fetchaMatch() {
        viewModelScope.launch {
            _matchState.value = Result.Loading

            try {
                val result = searchEventClient.byNameAndSeason("Arsenal_vs_Chelsea", "2016-2017")
                Log.e("TEEST ", "TEST $result")
                if (result != null) {
                    _matches.update { result.getOrNull() }

                } else {
                    _matchState.value = Result.Error(Exception("Failed to fetch weather"))
                }
            } catch (e: Exception) {
                _matchState.value = Result.Error(e)
            }
        }
    }
}
