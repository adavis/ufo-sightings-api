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
import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger

@Suppress("unused")
class UFOSightingsApp : Kotless() {
    override fun prepare(app: Application) {
        with(app) {
            main()
            routes()
        }
    }
}

fun Application.main() {
    startKoin {
        PrintLogger()
        modules(mainModule)
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    importData()

    log.info("Application setup complete")
}