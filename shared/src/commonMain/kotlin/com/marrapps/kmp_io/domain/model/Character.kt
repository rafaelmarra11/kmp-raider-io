package com.marrapps.kmp_io.domain.model

data class Character(
    val name: String = "",
    val race: String = "",
    val charClass: String = "",
    val faction: String = "",
    val spec: String = "",
    val role: String = "",
    val region: String = "",
    val realm: String = "",
    val thumbnailUrl: String = "",
    val scores: List<ScoreDisplay> = emptyList(),
)

data class ScoreDisplay(
    val score: Int = 0,
    val color: String = "",
    val role: String = ""
)