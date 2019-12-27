package info.adavis

import com.google.gson.Gson
import info.adavis.graphql.AppSchema
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.content.file
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.inject
import java.io.File

@Suppress("unused")
fun Application.routes() {
    routing {
        val appSchema: AppSchema by inject()
        val gson: Gson by inject()

        graphql(log, gson, appSchema.schema)

        static {
            staticRootFolder = File("src/main/static")
            file("index.html")
        }

        get("/") {
            call.respondRedirect("index.html")
        }
    }
}