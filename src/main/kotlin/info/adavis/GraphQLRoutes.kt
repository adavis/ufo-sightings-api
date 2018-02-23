package info.adavis

import com.github.pgutkowski.kgraphql.schema.Schema
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import org.slf4j.Logger

@Location("/graphql")
data class GraphQLRequest(val query: String = "")

fun Route.graphql(log: Logger, schema: Schema) {
    post<GraphQLRequest> {
        val request = call.receive<GraphQLRequest>()
        val query = request.query
        log.info("the graphql query: $query")

        call.respondText(schema.execute(query), ContentType.Application.Json)
    }
}