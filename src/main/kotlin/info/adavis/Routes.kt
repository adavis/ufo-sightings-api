package info.adavis

import io.ktor.application.Application
import io.ktor.application.log
import io.ktor.content.default
import io.ktor.content.static
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

@Suppress("unused")
fun Application.routes() {

    routing {
        val appSchema: AppSchema by inject()

        graphql(log, appSchema.schema)

        static("/") {
            default("index.html")
        }
    }
}