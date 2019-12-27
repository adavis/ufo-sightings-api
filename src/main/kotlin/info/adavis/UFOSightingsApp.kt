package info.adavis

import info.adavis.dao.importData
import info.adavis.di.mainModule
import io.kotless.dsl.ktor.Kotless
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.routing
import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger

@KtorExperimentalLocationsAPI
@Suppress("unused")
class UFOSightingsApp : Kotless() {
    override fun prepare(app: Application) {
        with(app) {
            main()
            routing { routes() }
        }
    }
}

@KtorExperimentalLocationsAPI
fun Application.main() {
    startKoin {
        PrintLogger()
        modules(mainModule)
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    importData()

    log.info("Application setup complete")
}