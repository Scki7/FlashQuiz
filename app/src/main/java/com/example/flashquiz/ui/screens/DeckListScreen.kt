package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Deck
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.ui.components.DeckCard
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun DeckListScreen(
    decks: List<Deck>,
    onDeckClick: (Int) -> Unit,
    onAddDeck: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = decks,
                key = { deck -> deck.id }
            ) { deck ->
                DeckCard(
                    name = deck.name,
                    cardCount = deck.cards.size,
                    onClick = { onDeckClick(deck.id) }
                )
            }
        }
        Button(
            onClick = onAddDeck,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Add deck")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeckListScreenPreview() {
    val sampleDecks = listOf(
        Deck(
            id = 1,
            name = "Kotlin Basics",
            cards = listOf(
                Flashcard(id = 1, question = "Read-only variable?", answer = "val"),
                Flashcard(id = 2, question = "Mutable variable?", answer = "var")
            )
        ),
        Deck(
            id = 2,
            name = "Jetpack Compose",
            cards = listOf(
                Flashcard(id = 3, question = "List component?", answer = "LazyColumn")
            )
        ),
        Deck(
            id = 3,
            name = "Android Architecture",
            cards = emptyList()
        )
    )

    FlashQuizTheme {
        DeckListScreen(
            decks = sampleDecks,
            onDeckClick = {},
            onAddDeck = {}
        )
    }
}