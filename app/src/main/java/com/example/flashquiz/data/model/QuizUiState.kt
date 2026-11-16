package com.example.flashquiz.data.model

data class QuizUiState(val deckId: Int = -1,
    val cards: List<Flashcard> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val lastAnswerCorrect: Boolean? = null,
    val isFinished: Boolean = false)
{

    val currentCard: Flashcard? get() = cards.getOrNull(currentIndex)
    val total: Int get() = cards.size
}