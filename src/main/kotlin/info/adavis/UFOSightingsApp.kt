package info.adavis

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.content.default
import io.ktor.content.static
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.Routing

lateinit var sportsApiUsername: String
lateinit var sportsApiPassword: String

@Suppress("unused")
fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Routing) {
        sportsSources()
        graphql(log)

        static("/") {
            default("index.html")
        }
    }

    with(environment.config) {
        sportsApiUsername = property("sports_api_username").getString()
        sportsApiPassword = property("sports_api_password").getString()
    }

    log.info("Application setup complete")
}
