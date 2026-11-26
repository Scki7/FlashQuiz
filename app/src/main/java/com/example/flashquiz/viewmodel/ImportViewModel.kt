package com.example.flashquiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashquiz.data.model.ImportUiState
import com.example.flashquiz.data.model.TriviaCategory
import com.example.flashquiz.data.repository.TriviaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImportViewModel(
    private val repository: TriviaRepository = TriviaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ImportUiState>(ImportUiState.Idle)
    val uiState: StateFlow<ImportUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow(TriviaCategory.COMPUTERS)
    val selectedCategory: StateFlow<TriviaCategory> = _selectedCategory.asStateFlow()

    fun selectCategory(category: TriviaCategory) {
        _selectedCategory.value = category
    }

    fun load() {
        if (_uiState.value is ImportUiState.Loading) return
        _uiState.value = ImportUiState.Loading
        viewModelScope.launch {
            repository.getQuestions(
                amount = 10,
                categoryId = _selectedCategory.value.id
            )
                .onSuccess { cards ->
                    _uiState.value = if (cards.isEmpty()) {
                        ImportUiState.Empty
                    } else {
                        ImportUiState.Success(cards)
                    }
                }
                .onFailure { error ->
                    _uiState.value = ImportUiState.Error(error.message ?: "Something went wrong")
                }
        }
    }

    fun reset() {
        _uiState.value = ImportUiState.Idle
    }
}