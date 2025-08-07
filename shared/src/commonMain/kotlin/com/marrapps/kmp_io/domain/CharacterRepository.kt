package com.marrapps.kmp_io.domain

import com.marrapps.kmp_io.domain.model.Character

interface CharacterRepository {

    suspend fun getCharacter(region: String, realm: String, name: String): Character
}