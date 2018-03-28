package info.adavis

import com.github.pgutkowski.kgraphql.KGraphQL
import info.adavis.dao.UFOSightingStorage
import info.adavis.model.CountrySightings
import info.adavis.model.UFOSighting
import org.koin.standalone.KoinComponent
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
            resolver { size: Long? -> storage.getAll(size ?: 10) }.withArgs {
                arg<Long> { name = "size"; defaultValue = 10; description = "The number of records to return" }
            }
        }

        query("sighting") {
            resolver { id: Int -> storage.getSighting(id) ?: throw NotFoundException("Sighting with id: $id does not exist") }
        }

        query("topSightings") {
            resolver(storage::getTopSightings)
        }

        type<UFOSighting> {
            description = "A UFO sighting"
        }

        type<CountrySightings> {
            description = "A country sighting; contains total number of occurrences"
        }
    }

}