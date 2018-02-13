package info.adavis

import info.adavis.Injection.schema
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receiveText
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post
import org.slf4j.Logger

data class GraphQLRequest(val query: String)

fun Route.graphql(log: Logger) {
    post("/graphql") {
        val query = Injection.gson.fromJson(call.receiveText(), GraphQLRequest::class.java).query
        log.info("the graphql query: $query")

        call.respondText(schema.execute(query), ContentType.Application.Json)
    }
}