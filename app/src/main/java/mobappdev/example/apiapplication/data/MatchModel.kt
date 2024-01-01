import org.json.JSONObject

data class Match(
    val strHomeTeam: String,
    val strAwayTeam: String,
    val intHomeScore: Int,
    val intAwayScore: Int,
)
data class Matches (
    val event: List<Match>
)
fun parseToMatch(responseData: JSONObject): Match {
    val strHomeTeam = responseData.optString("strHomeTeam")
    val strAwayTeam = responseData.optString("strAwayTeam")
    val intHomeScore = responseData.optInt("intHomeScore")
    val intAwayScore = responseData.optInt("intAwayScore")

    return Match(strHomeTeam, strAwayTeam, intHomeScore, intAwayScore)
}

data class UpcomingMatch(
    val idEvent: Int,
    val strHomeTeam: String,
    val idHomeTeam: Int,
    val strAwayTeam: String,
    val idAwayTeam: Int,
    var strHomeTeamBadge: String,
    var strAwayTeamBadge: String,
    val strVenue: String,
    val dateEvent: String,
    val strTime: String,
)
data class UpcomingMatches (
    val events: List<UpcomingMatch>
)

data class PastMatch(
    val idEvent: Int,
    val strHomeTeam: String,
    val idHomeTeam: Int,
    val strAwayTeam: String,
    val idAwayTeam: Int,
    var strHomeTeamBadge: String,
    var strAwayTeamBadge: String,
    val intHomeScore: Int,
    val intAwayScore: Int,
    val strVenue: String,
    val dateEvent: String,
    val strTime: String,
)
data class PastMatches (
    val results: List<PastMatch>
)