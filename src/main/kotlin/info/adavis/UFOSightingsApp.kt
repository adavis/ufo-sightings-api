package info.adavis

import com.github.kittinunf.fuel.httpGet
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.Routing
import io.ktor.routing.get

const val SPORTS_BASE_URL = "https://api.mysportsfeeds.com/v1.2/pull/nba"

lateinit var sportsApiUsername: String
lateinit var sportsApiPassword: String

@Suppress("unused")
fun Application.main() {
    install(DefaultHeaders)

    install(CallLogging)

    install(Routing) {
        get("/") {
            call.respondText("Hello!", ContentType.Text.Html)
        }
        sportsSources()
    }

    with(environment.config) {
        sportsApiUsername = property("sports_api_username").getString()
        sportsApiPassword = property("sports_api_password").getString()
    }

    log.info("Application setup complete")
}

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