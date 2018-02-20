package info.adavis

import com.github.kittinunf.fuel.httpGet
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

const val SPORTS_BASE_URL = "https://api.mysportsfeeds.com/v1.2/pull/nba"

fun Route.sportsSources() {
    get("/cumulative-player-stats") {
        val (_, _, result) =
                "$SPORTS_BASE_URL/2016-2017-regular/cumulative_player_stats.json"
                        .httpGet(listOf("playerstats" to "2PA,2PM,3PA,3PM,FTA,FTM", "limit" to 10))
                        .authenticate(sportsApiUsername, sportsApiPassword)
                        .responseString()

        call.respondText(result.get(), ContentType.Application.Json)
    }
}