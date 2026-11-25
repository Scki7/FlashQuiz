package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.model.LearnUiState
import com.example.flashquiz.ui.theme.CorrectBackground
import com.example.flashquiz.ui.theme.CorrectText
import com.example.flashquiz.ui.theme.FlashQuizTheme
import com.example.flashquiz.ui.theme.IncorrectBackground
import com.example.flashquiz.ui.theme.IncorrectText

@Composable
fun LearnScreen(
    state: LearnUiState,
    onAnswerSelected: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Back button
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        // Header
        Text(
            text = "Learn",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Practice your knowledge",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (state.isFinished) {

            // Learning results
            Text(
                text = "Learning completed!",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Correct answers: ${state.correctAnswers}"
            )

            Text(
                text = "Wrong answers: ${state.wrongAnswers}"
            )

        } else {

            val card = state.currentCard

            if (card != null) {

                // Question progress
                Text(
                    text = "Question ${state.currentIndex + 1} of ${state.total}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                LinearProgressIndicator(
                    progress = {
                        state.currentIndex.toFloat() /
                                state.total.coerceAtLeast(1)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Question card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = card.question,
                        modifier = Modifier.padding(24.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = "Choose the correct answer",
                    style = MaterialTheme.typography.titleMedium
                )

                // Answer options
                state.answerOptions.forEach { option ->

                    val isAnswered = state.selectedAnswer != null

                    val isCorrectOption = option.trim().equals(
                        card.answer.trim(),
                        ignoreCase = true
                    )

                    val isSelectedOption =
                        option == state.selectedAnswer

                    // Background color
                    val backgroundColor = when {
                        isAnswered && isCorrectOption ->
                            CorrectBackground

                        isAnswered && isSelectedOption ->
                            IncorrectBackground

                        else ->
                            MaterialTheme.colorScheme.surface
                    }

                    // Text color
                    val textColor = when {
                        isAnswered && isCorrectOption ->
                            CorrectText

                        isAnswered && isSelectedOption ->
                            IncorrectText

                        else ->
                            MaterialTheme.colorScheme.onSurface
                    }

                    OutlinedCard(
                        onClick = {
                            if (!isAnswered) {
                                onAnswerSelected(option)
                            }
                        },
                        enabled = !isAnswered,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = backgroundColor,
                            disabledContainerColor = backgroundColor,
                            contentColor = textColor,
                            disabledContentColor = textColor
                        )
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(18.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}
@Preview(
    name = "Learn - Default",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LearnScreenPreview() {
    FlashQuizTheme {
        LearnScreen(
            state = LearnUiState(
                deckId = 1,
                cards = listOf(
                    Flashcard(
                        id = 1,
                        question = "Keyword for a read-only variable?",
                        answer = "val",
                        wrongAnswers = listOf(
                            "var",
                            "fun",
                            "class"
                        )
                    )
                ),
                answerOptions = listOf(
                    "var",
                    "val",
                    "fun",
                    "class"
                )
            ),
            onAnswerSelected = {},
            onBack = {}
        )
    }
}
@Preview(
    name = "Learn - Incorrect Answer",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LearnScreenIncorrectPreview() {
    FlashQuizTheme {
        LearnScreen(
            state = LearnUiState(
                deckId = 1,
                cards = listOf(
                    Flashcard(
                        id = 1,
                        question = "Keyword for a read-only variable?",
                        answer = "val",
                        wrongAnswers = listOf(
                            "var",
                            "fun",
                            "class"
                        )
                    )
                ),
                answerOptions = listOf(
                    "var",
                    "val",
                    "fun",
                    "class"
                ),
                selectedAnswer = "var",
                wrongAnswers = 1
            ),
            onAnswerSelected = {},
            onBack = {}
        )
    }
}
