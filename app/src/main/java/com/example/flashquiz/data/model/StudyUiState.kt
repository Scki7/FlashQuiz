package com.example.flashquiz.data.model

data class StudyUiState(
    val deckId: Int = -1,
    val cards: List<Flashcard> = emptyList(),
    val currentIndex: Int = 0,
    val isFlipped: Boolean = false,
    val knownIds: Set<Int> = emptySet()
) {
    val currentCard: Flashcard? get() = cards.getOrNull(currentIndex)
    val total: Int get() = cards.size
    val knownCount: Int get() = knownIds.size
    val isFirst: Boolean get() = currentIndex == 0
    val isLast: Boolean get() = currentIndex >= cards.size - 1
    val isCurrentKnown: Boolean get() = currentCard?.id in knownIds
}