package info.adavis.graphql

import info.adavis.model.UFOSighting
import java.time.LocalDate

fun CreateUFOSightingInput.toUFOSighting() : UFOSighting {
    return UFOSighting(
            date = this.date,
            city = this.city,
            state = this.state,
            country = this.country,
            shape = this.shape,
            duration = this.duration,
            comments = this.comments,
            latitude = this.latitude,
            longitude = this.longitude
    )
}

data class CreateUFOSightingInput(
        var date: LocalDate = LocalDate.now(),
        var city: String? = "",
        var state: String? = "",
        var country: String? = "",
        var shape: String? = "",
        var duration: Double = 0.0,
        var comments: String? = "",
        var latitude: Double = 0.0,
        var longitude: Double = 0.0
)