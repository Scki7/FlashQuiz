package com.example.flashquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.luminance
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.model.LearnUiState
import com.example.flashquiz.ui.theme.CorrectBackground
import com.example.flashquiz.ui.theme.CorrectBackgroundDark
import com.example.flashquiz.ui.theme.CorrectTextDark
import com.example.flashquiz.ui.theme.IncorrectBackgroundDark
import com.example.flashquiz.ui.theme.IncorrectTextDark
import com.example.flashquiz.ui.theme.CorrectText
import com.example.flashquiz.ui.theme.FlashQuizTheme
import com.example.flashquiz.ui.theme.IncorrectBackground
import com.example.flashquiz.ui.theme.IncorrectText

@Composable
fun LearnScreen(
    state: LearnUiState,
    onAnswerSelected: (String) -> Unit,
    onContinue: () -> Unit,
    onPracticeAgain: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val correctBackground = if (isDark) CorrectBackgroundDark else CorrectBackground
    val correctText = if (isDark) CorrectTextDark else CorrectText
    val incorrectBackground = if (isDark) IncorrectBackgroundDark else IncorrectBackground
    val incorrectText = if (isDark) IncorrectTextDark else IncorrectText

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Back button
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        // Header
        Text(
            text = "Learn",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Practice your knowledge",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        if (state.isFinished) {

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Learning completed!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${state.correctAnswers} / ${state.total}",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Cards mastered",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    LinearProgressIndicator(
                        progress = { 1f },
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Correct answers")
                        Text(
                            text = "${state.correctAnswers}",
                            color = correctText,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Wrong attempts")
                        Text(
                            text = "${state.wrongAnswers}",
                            color = incorrectText,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Button(
                onClick = onPracticeAgain,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Practice Again",
                    modifier = Modifier.padding(8.dp)
                )
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Back to Deck",
                    modifier = Modifier.padding(8.dp)
                )
            }


        } else {

            val card = state.currentCard

            if (card != null) {

                // Question progress
                Text(
                    text = "Mastered ${state.correctAnswers} of ${state.total}",                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                LinearProgressIndicator(
                    progress = {
                        state.correctAnswers.toFloat() /
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
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
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
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
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
                            correctBackground

                        isAnswered && isSelectedOption ->
                            incorrectBackground

                        else ->
                            MaterialTheme.colorScheme.surface
                    }

                    // Text color
                    val textColor = when {
                        isAnswered && isCorrectOption ->
                            correctText

                        isAnswered && isSelectedOption ->
                            incorrectText

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

                if (state.selectedAnswer != null) {
                    Button(
                        onClick = onContinue,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Continue",
                            modifier = Modifier.padding(8.dp)
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
                originalTotal = 1,
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
            onPracticeAgain = {},
            onContinue ={} ,
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
                originalTotal = 1,
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
            onContinue = {},
            onPracticeAgain = {},
            onBack = {}
        )
    }
}

@Preview(
    name = "Learn - Completed",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LearnScreenCompletedPreview() {
    FlashQuizTheme {
        LearnScreen(
            state = LearnUiState(
                deckId = 1,
                originalTotal = 3,
                correctAnswers = 3,
                wrongAnswers = 2,
                isFinished = true
            ),
            onAnswerSelected = {},
            onContinue = {},
            onPracticeAgain = {},
            onBack = {}
        )
    }
}
