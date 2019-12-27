package info.adavis.di

import com.google.gson.Gson
import info.adavis.graphql.AppSchema
import info.adavis.dao.UFOSightingDatabase
import info.adavis.dao.UFOSightingStorage
import org.koin.dsl.module

val mainModule = module(createdAtStart = true) {
    single { Gson() }
    single { AppSchema(get()) }
    single { UFOSightingDatabase() as UFOSightingStorage }
}