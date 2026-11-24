
package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.LearnUiState
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.ui.theme.FlashQuizTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight

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
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

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
            Text(
                text = "Learning completed!",
                style = MaterialTheme.typography.titleLarge
            )

            Text("Correct answers: ${state.correctAnswers}")
            Text("Wrong answers: ${state.wrongAnswers}")

        } else {
            val card = state.currentCard

            if (card != null) {

                Text(
                    text = "Question ${state.currentIndex + 1} of ${state.total}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                LinearProgressIndicator(
                    progress = {
                        state.currentIndex.toFloat() / state.total.coerceAtLeast(1)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
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

                state.answerOptions.forEach { option ->
                    OutlinedCard(
                        onClick = { onAnswerSelected(option) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(18.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
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
                        wrongAnswers = listOf("var", "fun", "class")
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
