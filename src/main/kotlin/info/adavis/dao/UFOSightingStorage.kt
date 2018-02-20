package info.adavis.dao

import info.adavis.model.UFOSighting
import java.io.Closeable

interface UFOSightingStorage : Closeable {

    fun createSighting(sighting: UFOSighting): Int

    fun getSighting(id: Int): UFOSighting?

    fun getAll(size: Long): List<UFOSighting>
}