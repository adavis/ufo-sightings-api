package info.adavis.dao

import info.adavis.dao.UFOSightings.id
import info.adavis.model.UFOSighting
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.*
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.results.get
import org.jetbrains.squash.schema.create
import org.jetbrains.squash.statements.fetch
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.values

fun ResultRow.toUFOSighting() = UFOSighting(
            id = this[UFOSightings.id],
            date = this[UFOSightings.date],
            city = this[UFOSightings.city],
            state = this[UFOSightings.state],
            country = this[UFOSightings.country],
            shape = this[UFOSightings.shape],
            duration = this[UFOSightings.duration].toDouble(),
            comments = this[UFOSightings.comments],
            latitude = this[UFOSightings.latitude].toDouble(),
            longitude = this[UFOSightings.longitude].toDouble()
    )

class UFOSightingDatabase(val db: DatabaseConnection = H2Connection.createMemoryConnection()) : UFOSightingStorage {

    init {
        db.transaction {
            databaseSchema().create(UFOSightings)
        }
    }

    override fun getSighting(id: Int) = db.transaction {
        val row = from(UFOSightings).where { UFOSightings.id eq id }.execute().singleOrNull()
        row?.toUFOSighting()
    }

    override fun getAll(size: Long): List<UFOSighting>  = db.transaction {
        from(UFOSightings)
                .select()
                .orderBy(UFOSightings.date, false)
                .limit(size)
                .execute()
                .map { it.toUFOSighting() }
                .toList()
    }

    override fun createSighting(sighting: UFOSighting) = db.transaction {
        insertInto(UFOSightings).values {
            it[date] = sighting.date
            it[city] = sighting.city
            it[state] = sighting.state
            it[country] = sighting.country
            it[shape] = sighting.shape
            it[duration] = sighting.duration.toBigDecimal()
            it[comments] = sighting.comments
            it[latitude] = sighting.latitude.toBigDecimal()
            it[longitude] = sighting.longitude.toBigDecimal()
        }.fetch(id).execute()
    }

    override fun close() {
    }
}