package mobappdev.example.apiapplication.data

data class Leagues(
    val leagues: List<League>
)

data class League(
    val idLeague: Int,
    val strLeague: String,
    val strSport: String,
    val strLeagueAlternate: String
)