package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        // DeckCard-та padding(horizontal = 16.dp, vertical = 6.dp) бар болғандықтан,
        // мұнда тек үсті мен астына ғана сәл бос орын береміз (екі еселенбеуі үшін)
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
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
}

@Preview(showBackground = true)
@Composable
fun DeckListScreenPreview() {
    val sampleDecks = listOf(
        Deck(
            id = 1,
            name = "Kotlin Basics",
            cards = listOf(
                Flashcard(id = 1, question = "Read-only айнымалы?", answer = "val"),
                Flashcard(id = 2, question = "Өзгеретін айнымалы?", answer = "var")
            )
        ),
        Deck(
            id = 2,
            name = "Jetpack Compose",
            cards = listOf(
                Flashcard(id = 3, question = "Тізім шығаратын компонент?", answer = "LazyColumn")
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
            onDeckClick = {}
        )
    }
}
