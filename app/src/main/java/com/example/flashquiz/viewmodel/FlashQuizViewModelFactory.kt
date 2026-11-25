package com.example.flashquiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flashquiz.data.repository.FlashQuizRepository

class FlashQuizViewModelFactory(
    private val repository: FlashQuizRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(FlashQuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashQuizViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
