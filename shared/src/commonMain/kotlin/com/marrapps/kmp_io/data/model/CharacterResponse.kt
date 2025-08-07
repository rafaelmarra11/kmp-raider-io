package com.marrapps.kmp_io.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterResponse(
    @SerialName("name")
    val name: String?,
    @SerialName("race")
    val race: String?,
    @SerialName("class")
    val charClass: String?,
    @SerialName("faction")
    val faction: String?,
    @SerialName("active_spec_name")
    val spec: String?,
    @SerialName("active_spec_role")
    val role: String?,
    @SerialName("region")
    val region: String?,
    @SerialName("realm")
    val realm: String?,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerialName("mythic_plus_scores_by_season")
    val scores: List<Scores>?
)

@Serializable
internal data class Scores(
    @SerialName("season")
    val season: String?,
    @SerialName("segments")
    val segments: Segments?
)

@Serializable
internal data class Segments(
    @SerialName("all")
    val all: Score?,
    @SerialName("dps")
    val dps: Score?,
    @SerialName("healer")
    val healer: Score?,
    @SerialName("tank")
    val tank: Score?
)

@Serializable
internal data class Score(
    @SerialName("score")
    val score: Double?,
    @SerialName("color")
    val color: String?
)
