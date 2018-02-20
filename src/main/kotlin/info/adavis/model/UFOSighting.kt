package info.adavis.model

import java.time.LocalDate

data class UFOSighting(
        var id: Int = -1,
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