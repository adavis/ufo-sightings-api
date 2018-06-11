package info.adavis.graphql

import com.github.pgutkowski.kgraphql.KGraphQL
import info.adavis.NotFoundException
import info.adavis.dao.UFOSightingStorage
import info.adavis.model.CountrySightings
import info.adavis.model.UFOSighting
import info.adavis.model.User
import info.adavis.model.users
import java.time.LocalDate

class AppSchema(private val storage: UFOSightingStorage) {

    val schema = KGraphQL.schema {

        configure {
            useDefaultPrettyPrinter = true
        }

        stringScalar<LocalDate> {
            serialize = { date -> date.toString() }
            deserialize = { dateString -> LocalDate.parse(dateString) }
        }

        query("sightings") {
            description = "Returns a subset of the UFO Sighting records"

            resolver { size: Int? -> storage.getAll(size?.toLong() ?: 10) }.withArgs {
                arg<Long> { name = "size"; defaultValue = 10; description = "The number of records to return" }
            }
        }

        query("sighting") {
            description = "Returns a single UFO Sighting record based on the id"

            resolver { id: Int -> storage.getSighting(id) ?: throw NotFoundException("Sighting with id: $id does not exist") }
        }

        query("user") {
            description = "Returns a single User based on the id"

            resolver { id: Int -> users.getOrNull(id) ?: throw NotFoundException("User with id: $id does not exist") }
        }

        query("topSightings") {
            description = "Returns a list of the top 10 state,country based on the number of sightings"

            resolver(storage::getTopSightings)
        }

        query("topCountrySightings") {
            description = "Returns a list of the top 10 countries based on the number of sightings"

            resolver(storage::getTopCountrySightings)
        }

        mutation("createUFOSighting") {
            description = "Adds a new UFO Sighting to the database"

            resolver { input: CreateUFOSightingInput -> storage.createSighting(input.toUFOSighting()) }
        }

        inputType<CreateUFOSightingInput>()

        type<UFOSighting> {
            description = "A UFO sighting"

            property(UFOSighting::date) {
                description = "The date of the sighting"
            }

            property<User>("user") {
                resolver { _ ->
                    users[(0..2).shuffled().last()]
                }
            }
        }

        type<CountrySightings> {
            description = "A country sighting; contains total number of occurrences"
        }

        type<User> {
            description = "A User who has reported a UFO sighting"

            property<UFOSighting?>("sighting") {
                resolver { user -> storage.getSighting(user.id) }
            }
        }
    }

}