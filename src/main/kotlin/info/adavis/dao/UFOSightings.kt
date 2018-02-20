package info.adavis.dao

import org.jetbrains.squash.definition.*

object UFOSightings : TableDefinition() {
    val id = integer("id").autoIncrement().primaryKey()
    val date = date("date")
    val city = varchar("city", 128)
    val state = varchar("state", 4)
    val country = varchar("country", 4)
    val shape = varchar("shape", 28)
    val duration = decimal("duration", 10, 2)
    val comments = varchar("comments", 1024)
    val latitude = decimal("latitude", 12, 8)
    val longitude = decimal("longitude", 12, 8)
}