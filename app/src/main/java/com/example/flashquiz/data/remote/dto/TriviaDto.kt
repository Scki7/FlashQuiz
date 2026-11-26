package com.example.flashquiz.data.remote.dto

import androidx.core.text.HtmlCompat
import com.example.flashquiz.data.model.Flashcard
import com.google.gson.annotations.SerializedName

data class TriviaResponseDto(
    @SerializedName("response_code") val responseCode: Int,
    val results: List<TriviaQuestionDto> = emptyList()
)

data class TriviaQuestionDto(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @SerializedName("correct_answer") val correctAnswer: String,
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String>
)

fun TriviaQuestionDto.toFlashcard(id: Int): Flashcard = Flashcard(
    id = id,
    question = question.decodeHtml(),
    answer = correctAnswer.decodeHtml(),
    wrongAnswers = incorrectAnswers.map { it.decodeHtml() }
)

private fun String.decodeHtml(): String =
    HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()