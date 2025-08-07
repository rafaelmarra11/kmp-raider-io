package com.marrapps.kmp_io.android.search.domain

import com.marrapps.kmp_io.domain.CharacterRepository

class GetCharacterUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(
        name: String,
        region: String,
        server: String
    ) = repository.getCharacter(
        name = name,
        region = region,
        realm = server
    )
}