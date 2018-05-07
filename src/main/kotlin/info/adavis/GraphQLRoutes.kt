package info.adavis

import com.github.pgutkowski.kgraphql.schema.Schema
import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import org.slf4j.Logger

@Location("/graphql")
data class GraphQLRequest(val query: String = "", val variables: Map<String, Any> = emptyMap())

fun Route.graphql(log: Logger, gson: Gson, schema: Schema) {
    post<GraphQLRequest> {
        val request = call.receive<GraphQLRequest>()

        val query = request.query
        log.info("the graphql query: $query")

        val variables = gson.toJson(request.variables)
        log.info("the graphql variables: $variables")

        call.respondText(schema.execute(query, variables), ContentType.Application.Json)
    }
}