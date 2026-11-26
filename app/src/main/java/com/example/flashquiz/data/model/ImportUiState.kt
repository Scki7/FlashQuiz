package com.example.flashquiz.data.model

sealed interface ImportUiState {
    data object Idle : ImportUiState
    data object Loading : ImportUiState
    data class Success(val questions: List<Flashcard>) : ImportUiState
    data object Empty : ImportUiState
    data class Error(val message: String) : ImportUiState
}