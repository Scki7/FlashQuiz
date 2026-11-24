package com.example.flashquiz.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.model.FlashcardUiState
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun FlashcardScreen(
    state: FlashcardUiState,
    onFlip: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onToggleKnown: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (state.isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "cardFlip"
    )

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Card ${state.currentIndex + 1} / ${state.total}",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "Known: ${state.knownCount}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 12f * density
                    }
                    .clickable { onFlip() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .graphicsLayer {
                            if (rotation > 90f) {
                                rotationY = 180f
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val isBack = rotation > 90f
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = if (isBack) "ANSWER" else "QUESTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (isBack) {
                                state.currentCard?.answer ?: ""
                            } else {
                                state.currentCard?.question ?: ""
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onToggleKnown,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.isCurrentKnown) "Unmark known" else "Mark as known")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onPrevious,
                enabled = !state.isFirst,
                modifier = Modifier.weight(1f)
            ) {
                Text("Previous")
            }
            Button(
                onClick = onNext,
                enabled = !state.isLast,
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlashcardScreenPreview() {
    FlashQuizTheme {
        FlashcardScreen(
            state = FlashcardUiState(
                cards = listOf(
                    Flashcard(1, "What is Jetpack Compose?", "Modern UI toolkit for Android")
                )
            ),
            onFlip = {},
            onNext = {},
            onPrevious = {},
            onToggleKnown = {},
            onBack = {}
        )
    }
}
