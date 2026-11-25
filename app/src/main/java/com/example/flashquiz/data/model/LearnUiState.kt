package com.example.flashquiz.data.model

data class LearnUiState(
    val deckId: Int = -1,
    val cards: List<Flashcard> = emptyList(),
    val currentIndex: Int = 0,
    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val isFinished: Boolean = false,
    val answerOptions: List<String> = emptyList(),
    val retryCards: List<Flashcard> = emptyList(),
    val selectedAnswer: String? = null,
    val originalTotal: Int = 0,
) {
    val currentCard: Flashcard?
        get() = cards.getOrNull(currentIndex)

    val total: Int
        get() = originalTotal
}