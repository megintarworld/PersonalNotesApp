package org.notes.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.notes.core.data.ServiceCrudNote
import org.notes.db.CreateDb
import org.notes.db.DbNote
import org.notes.di.contract.ServCrudNote

expect val moduleTarget: Module

val moduleShared = module {
    single<DbNote> { CreateDb(get()).getDb() }
    single<ServiceCrudNote> { ServCrudNote(get()) }
}