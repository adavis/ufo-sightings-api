package info.adavis

import com.google.gson.Gson
import info.adavis.graphql.AppSchema
import io.ktor.application.Application
import io.ktor.application.log
import io.ktor.http.content.default
import io.ktor.http.content.static
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
@Suppress("unused")
fun Application.routes() {

    routing {
        val appSchema: AppSchema by inject()
        val gson: Gson by inject()

        graphql(log, gson, appSchema.schema)

        static("/") {
            default("index.html")
        }
    }
}