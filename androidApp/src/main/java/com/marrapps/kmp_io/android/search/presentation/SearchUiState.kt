package com.marrapps.kmp_io.android.search.presentation

data class SearchUiState(
    val name: String = "",
    val region: String = "",
    val server: String = "",
    val serverList: List<String> = emptyList()
)
