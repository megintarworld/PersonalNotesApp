package org.notes.home

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.notes.home.data.repo.ImplRepoHome
import org.notes.home.domain.repo.RepoHome
import org.notes.home.domain.uc.UcHome
import org.notes.home.domain.vm.VmHome

val moduleHome = module {
    single<RepoHome> { ImplRepoHome(get()) }
    single { UcHome(get()) }

    viewModelOf(::VmHome)
}