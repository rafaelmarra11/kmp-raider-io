package com.marrapps.kmp_io.data

import com.marrapps.kmp_io.data.model.CharacterResponse
import com.marrapps.kmp_io.data.model.Score
import com.marrapps.kmp_io.domain.CharacterRepository
import com.marrapps.kmp_io.domain.model.Character
import com.marrapps.kmp_io.domain.model.ScoreDisplay
import kotlin.math.roundToInt

internal class CharacterRepositoryImpl(private val api: RaiderIoApi) : CharacterRepository {

    override suspend fun getCharacter(region: String, realm: String, name: String): Character {
        return api.getCharacter(region, realm, name).mapToDomain()
    }

    private fun CharacterResponse.mapToDomain() = Character(
        name = name.orEmpty(),
        race = race.orEmpty(),
        charClass = charClass.orEmpty(),
        faction = faction.orEmpty(),
        spec = spec.orEmpty(),
        role = role.orEmpty(),
        region = region.orEmpty(),
        realm = realm.orEmpty(),
        thumbnailUrl = thumbnailUrl.orEmpty(),
        scores = buildScoresList()
    )

    private fun CharacterResponse.buildScoresList(): List<ScoreDisplay> {
        val dpsScore = scores?.firstOrNull()?.segments?.dps?.toScoreDisplay("Dps")
        val healerScore = scores?.firstOrNull()?.segments?.healer?.toScoreDisplay("Healer")
        val tankScore = scores?.firstOrNull()?.segments?.tank?.toScoreDisplay("Tank")

        return listOfNotNull(dpsScore, healerScore, tankScore)
    }

    private fun Score.toScoreDisplay(role: String) = ScoreDisplay(
        score?.roundToInt() ?: 0,
        color.orEmpty(),
        role
    )
}
