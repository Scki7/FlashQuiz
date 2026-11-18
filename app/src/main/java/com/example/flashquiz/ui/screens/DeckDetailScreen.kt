package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Deck
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun DeckDetailScreen(deck: Deck?, modifier: Modifier = Modifier) {
    if (deck == null) {
        Text("Deck not found", modifier = modifier.padding(24.dp))
    } else {
        Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
            Text(deck.name, style = MaterialTheme.typography.headlineMedium)
            Text("${deck.cards.size} cards", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeckDetailScreenPreview() {
    FlashQuizTheme {
        DeckDetailScreen(
            deck = Deck(1, "Kotlin basics", listOf(Flashcard(1, "Q?", "A")))

        )
    }
}