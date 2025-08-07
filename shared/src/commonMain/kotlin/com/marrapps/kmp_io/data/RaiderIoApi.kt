package com.marrapps.kmp_io.data

import com.marrapps.kmp_io.data.model.CharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val CHARACTER_BASE_URL = "https://raider.io/api/v1/characters/profile"
private const val CURRENT_SEASON_FIELD = "mythic_plus_scores_by_season:current"

internal class RaiderIoApi(private val httpClient: HttpClient) {

    suspend fun getCharacter(region: String, realm: String, name: String): CharacterResponse {
        return httpClient.get(CHARACTER_BASE_URL) {
            url {
                parameters.append("region", region)
                parameters.append("realm", realm)
                parameters.append("name", name)
                parameters.append("fields", CURRENT_SEASON_FIELD)
            }
        }.body()
    }
}