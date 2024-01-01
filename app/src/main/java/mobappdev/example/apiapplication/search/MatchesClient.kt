package mobappdev.example.apiapplication.search

import PastMatches
import UpcomingMatches

interface MatchesClient {
    suspend fun searchUpcomingMatchesById(id: Int): Result<UpcomingMatches>
    suspend fun searchPastMatchesById(id: Int): Result<PastMatches>
}
