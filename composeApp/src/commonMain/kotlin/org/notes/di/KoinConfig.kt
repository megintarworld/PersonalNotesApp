package org.notes.di

import org.koin.dsl.KoinConfiguration
import org.notes.home.moduleHome

fun createKoinConfig(): KoinConfiguration {
    return KoinConfiguration{
        modules(
            moduleTarget,
            moduleShared,
            moduleHome
        )
    }
}