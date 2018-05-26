package info.adavis.dao

import info.adavis.model.CountrySightings
import info.adavis.model.UFOSighting
import java.io.Closeable

interface UFOSightingStorage : Closeable {

    fun createSighting(sighting: UFOSighting): UFOSighting

    fun getSighting(id: Int): UFOSighting?

    fun getAll(size: Long): List<UFOSighting>

    fun getTopSightings(): List<CountrySightings>

    fun getTopCountrySightings(): List<CountrySightings>
}