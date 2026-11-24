package com.example.flashquiz.data.model

data class Flashcard(
    val id: Int,
    val question: String,
    val answer: String,
    val wrongAnswers: List<String> = emptyList()
)