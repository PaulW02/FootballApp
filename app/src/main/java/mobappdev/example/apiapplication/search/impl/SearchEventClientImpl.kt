package mobappdev.example.apiapplication.search.impl

import mobappdev.example.apiapplication.AbstractJsportsClient
import mobappdev.example.apiapplication.model.ResultResponse
import mobappdev.example.apiapplication.search.SearchEventClient


/**
 * Created by Arthur Asatryan.
 * Date: 11/17/19
 * Time: 2:54 PM
 */
class SearchEventClientImpl : SearchEventClient, AbstractJsportsClient() {
    override fun byName(name: String): ResultResponse {
        val request = requestBuilder("/searchevents.php?e=$name")
        return ResultResponse(handleClientCall(request), objectMapper)
    }

    override fun byNameAndSeason(name: String, season: Int): ResultResponse {
        val request = requestBuilder("/searchevents.php?e=$name&s=$season")
        return ResultResponse(handleClientCall(request), objectMapper)
    }
}