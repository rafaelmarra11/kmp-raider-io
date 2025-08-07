package com.marrapps.kmp_io.android.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marrapps.kmp_io.android.search.domain.GetCharacterUseCase
import com.marrapps.kmp_io.android.search.domain.euRealmNames
import com.marrapps.kmp_io.android.search.domain.usRealmNames
import com.marrapps.kmp_io.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _resultState = MutableStateFlow<SearchCharacterState>(
        SearchCharacterState.Empty
        /*SearchCharacterState.Success(
            Character(
                thumbnailUrl = "https://render.worldofwarcraft.com/us/character/thrall/193/199153089-avatar.jpg?alt=/wow/static/images/2d/avatar/10-1.jpg",
                name = "Archville",
                race = "Blood Elf",
                charClass = "Demon Hunter",
                spec = "Havoc",
                scores = listOf(
                    ScoreDisplay(role = "Dps", score = 455, color = "#e9ffe0")
                )
            )
        )*/
    )
    val resultState: StateFlow<SearchCharacterState> = _resultState.asStateFlow()

    sealed interface SearchCharacterState {
        data object Empty : SearchCharacterState
        data object Loading : SearchCharacterState
        data class Error(val error: Throwable) : SearchCharacterState
        data class Success(val character: Character) : SearchCharacterState
    }

    fun saveNameInput(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun saveRegionInput(region: String) {
        val serverList = when (region) {
            "US" -> usRealmNames
            "EU" -> euRealmNames
            else -> emptyList()
        }
        _uiState.value = _uiState.value.copy(region = region, serverList = serverList)
    }

    fun saveServerInput(server: String) {
        _uiState.value = _uiState.value.copy(server = server)
    }

    fun onSearchButtonClicked() {
        viewModelScope.launch {
            try {
                with(uiState.value) {
                    _resultState.value = SearchCharacterState.Loading

                    val character = getCharacterUseCase(name, region, server)
                    _resultState.value = SearchCharacterState.Success(character)
                }
            } catch (error: Throwable) {
                _resultState.value = SearchCharacterState.Error(error)
            }
        }
    }
}