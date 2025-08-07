package com.marrapps.kmp_io

import com.marrapps.kmp_io.data.CharacterRepositoryImpl
import com.marrapps.kmp_io.data.RaiderIoApi
import com.marrapps.kmp_io.data.httpClient
import com.marrapps.kmp_io.domain.CharacterRepository
import org.koin.dsl.module

val kmpIoModule = module {
    factory<CharacterRepository> {
        CharacterRepositoryImpl(
            api = RaiderIoApi(httpClient)
        )
    }
}