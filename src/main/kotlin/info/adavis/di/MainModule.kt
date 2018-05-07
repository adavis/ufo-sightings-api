package info.adavis.di

import com.google.gson.Gson
import info.adavis.graphql.AppSchema
import info.adavis.dao.UFOSightingDatabase
import info.adavis.dao.UFOSightingStorage
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {
    provide { Gson() }
    provide { AppSchema(get()) }
    provide { UFOSightingDatabase() as UFOSightingStorage }
}