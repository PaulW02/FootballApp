package mobappdev.example.apiapplication.search.impl

import PastMatches
import UpcomingMatches
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mobappdev.example.apiapplication.AbstractJsportsClient
import mobappdev.example.apiapplication.lookup.LookupTeamClient
import mobappdev.example.apiapplication.lookup.impl.LookupTeamClientImpl
import mobappdev.example.apiapplication.search.MatchesClient
import java.net.HttpURLConnection

class MatchesClientImpl: MatchesClient, AbstractJsportsClient()
{
    override suspend fun searchUpcomingMatchesById(id: Int): Result<UpcomingMatches> {
        val lookupTeamClient: LookupTeamClient = LookupTeamClientImpl()
        return withContext(Dispatchers.IO) {
            try {
                val request = requestBuilder("/eventsnext.php?id=$id")
                val url = request.url.toUrl()

                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                // Use Gson to parse the JSON string into an UpcomingMatches object
                val type = object : TypeToken<UpcomingMatches>() {}.type
                val upcomingMatches = Gson().fromJson<UpcomingMatches>(json, type)

                // Iterate through each upcoming match and fetch team details
                for (event in upcomingMatches.events) {
                    if (event.idHomeTeam == id) {
                        // Update strHomeTeamBadge directly without making an additional call
                        event.strHomeTeamBadge = "BadgeURL"  // Replace with the actual badge URL
                        val awayTeamResult = lookupTeamClient.byId(event.idAwayTeam)

                        // Check if the call was successful
                        if (awayTeamResult.isSuccess) {
                            val awayTeam = awayTeamResult.getOrThrow()

                            // Update strAwayTeamBadge
                            event.strAwayTeamBadge = awayTeam.teams[0].strTeamBadge
                        } else {
                            // Handle the case where the byId call for the away team failed
                            // You can log an error or handle it based on your application's logic
                        }
                    } else if (event.idAwayTeam == id){
                        // If the current match is not the specified match, fetch away team details using byId
                        // Update strHomeTeamBadge directly without making an additional call
                        event.strAwayTeamBadge = "BadgeURL"  // Replace with the actual badge URL
                        val homeTeamResult = lookupTeamClient.byId(event.idHomeTeam)

                        // Check if the call was successful
                        if (homeTeamResult.isSuccess) {
                            val homeTeam = homeTeamResult.getOrThrow()

                            // Update strAwayTeamBadge
                            event.strHomeTeamBadge = homeTeam.teams[0].strTeamBadge
                        } else {
                            // Handle the case where the byId call for the away team failed
                            // You can log an error or handle it based on your application's logic
                        }
                    }
                }

                Result.success(upcomingMatches)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun searchPastMatchesById(id: Int): Result<PastMatches> {
        val lookupTeamClient: LookupTeamClient = LookupTeamClientImpl()
        return withContext(Dispatchers.IO) {
            try {
                val request = requestBuilder("/eventslast.php?id=$id")
                val url = request.url.toUrl()

                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val json = inputStream.bufferedReader().use { it.readText() }

                // Use Gson to parse the JSON string into an UpcomingMatches object
                val type = object : TypeToken<PastMatches>() {}.type
                val pastMatches = Gson().fromJson<PastMatches>(json, type)

                // Iterate through each upcoming match and fetch team details
                Log.e("CLIENT PAST ", pastMatches.toString())
                for (event in pastMatches.results) {
                    if (event.idHomeTeam == id) {
                        // Update strHomeTeamBadge directly without making an additional call
                        event.strHomeTeamBadge = "BadgeURL"  // Replace with the actual badge URL
                        val awayTeamResult = lookupTeamClient.byId(event.idAwayTeam)

                        // Check if the call was successful
                        if (awayTeamResult.isSuccess) {
                            val awayTeam = awayTeamResult.getOrThrow()

                            // Update strAwayTeamBadge
                            event.strAwayTeamBadge = awayTeam.teams[0].strTeamBadge
                        } else {
                            // Handle the case where the byId call for the away team failed
                            // You can log an error or handle it based on your application's logic
                        }
                    } else if (event.idAwayTeam == id){
                        // If the current match is not the specified match, fetch away team details using byId
                        // Update strHomeTeamBadge directly without making an additional call
                        event.strAwayTeamBadge = "BadgeURL"  // Replace with the actual badge URL
                        val homeTeamResult = lookupTeamClient.byId(event.idHomeTeam)

                        // Check if the call was successful
                        if (homeTeamResult.isSuccess) {
                            val homeTeam = homeTeamResult.getOrThrow()

                            // Update strAwayTeamBadge
                            event.strHomeTeamBadge = homeTeam.teams[0].strTeamBadge
                        } else {
                            // Handle the case where the byId call for the away team failed
                            // You can log an error or handle it based on your application's logic
                        }
                    }
                }

                Result.success(pastMatches)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}