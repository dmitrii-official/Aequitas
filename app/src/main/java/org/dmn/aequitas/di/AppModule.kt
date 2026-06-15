package org.dmn.aequitas.di

import org.dmn.aequitas.DetailsViewModel
import org.dmn.aequitas.data.RequestService
import org.dmn.aequitas.data.RequestServiceImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel {
        DetailsViewModel(
            requestService = get()
        )
    }

    single<RequestService> {
        RequestServiceImpl()
    }
}