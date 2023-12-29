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