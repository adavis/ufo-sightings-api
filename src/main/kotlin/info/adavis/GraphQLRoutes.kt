package info.adavis

import com.github.pgutkowski.kgraphql.schema.Schema
import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post
import org.slf4j.Logger

data class GraphQLRequest(val query: String)

fun Route.graphql(log: Logger, gson: Gson, schema: Schema) {
    post("/graphql") {
        val query = gson.fromJson(call.receive<String>(), GraphQLRequest::class.java).query
        log.info("the graphql query: $query")

        call.respondText(schema.execute(query), ContentType.Application.Json)
    }
}