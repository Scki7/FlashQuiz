package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.model.QuizUiState
import com.example.flashquiz.ui.theme.Correct
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun QuizScreen(
    state: QuizUiState,
    onSubmit: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var answer by rememberSaveable(state.currentIndex) { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Question ${state.currentIndex + 1} / ${state.total}",
            style = MaterialTheme.typography.labelLarge
        )
        Text("Score: ${state.score}", style = MaterialTheme.typography.bodyMedium)
        Text(
            state.currentCard?.question ?: "",
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Your answer") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onSubmit(answer) },
            enabled = answer.isNotBlank() && state.lastAnswerCorrect == null,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Check") }
        state.lastAnswerCorrect?.let { correct ->
            Text(
                text = if (correct) "Correct!" else "Wrong. Answer: ${state.currentCard?.answer}",
                color = if (correct) Correct else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium
            )
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) { Text("Next") }
        }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    FlashQuizTheme {
        QuizScreen(
            state = QuizUiState(
                cards = listOf(
                    Flashcard(1, "Keyword for a read-only variable?", "val"),
                    Flashcard(2, "Keyword for a mutable variable?", "var")
                )
            ),
            onSubmit = {},
            onNext = {},
            onBack = {}
        )
    }
}