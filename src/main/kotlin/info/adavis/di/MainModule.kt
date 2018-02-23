package info.adavis.di

import info.adavis.AppSchema
import info.adavis.dao.UFOSightingDatabase
import info.adavis.dao.UFOSightingStorage
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {
    provide { AppSchema(get()) }
    provide { UFOSightingDatabase() as UFOSightingStorage }
}