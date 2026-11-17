package com.example.flashquiz.data.model

data class Deck(
    val id: Int,
    val name: String,
    val cards: List<Flashcard> = emptyList()
)