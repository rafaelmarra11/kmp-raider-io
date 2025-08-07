package com.marrapps.kmp_io.android.search.di

import com.marrapps.kmp_io.android.search.domain.GetCharacterUseCase
import com.marrapps.kmp_io.android.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel {
        SearchViewModel(
            getCharacterUseCase = GetCharacterUseCase(
                repository = get()
            )
        )
    }
}