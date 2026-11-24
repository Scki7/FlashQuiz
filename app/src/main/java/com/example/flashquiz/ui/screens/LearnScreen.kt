
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
