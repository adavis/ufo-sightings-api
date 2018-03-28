package info.adavis.model

data class CountrySightings(var numOccurrences: Int = 0) {

    var state: String = ""
        get() = field.toUpperCase()

    var country: String = ""
        get() = field.toUpperCase()

}