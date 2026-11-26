package com.example.flashquiz.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.model.ImportUiState
import com.example.flashquiz.data.model.TriviaCategory

@Composable
fun ImportScreen(
    state: ImportUiState,
    selectedCategory: TriviaCategory,
    onCategorySelected: (TriviaCategory) -> Unit,
    onLoad: () -> Unit,
    onSave: (List<Flashcard>) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Import questions",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Load ready-made questions from the internet and save them as a deck.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TriviaCategory.entries.forEach { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category.title) }
                )
            }
        }

        Button(
            onClick = onLoad,
            enabled = state !is ImportUiState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load questions")
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (state) {
                ImportUiState.Idle -> Text(
                    text = "Choose a category and press Load questions.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                ImportUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                is ImportUiState.Success -> Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items = state.questions, key = { it.id }) { card ->
                            QuestionPreviewCard(card)
                        }
                    }
                    Button(
                        onClick = { onSave(state.questions) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(top = 8.dp)
                    ) {
                        Text("Save as deck")
                    }
                }

                ImportUiState.Empty -> Text(
                    text = "No questions available for this category.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )

                is ImportUiState.Error -> Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onLoad) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionPreviewCard(card: Flashcard) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = card.question,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Answer: ${card.answer}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}