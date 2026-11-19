package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Deck
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.ui.theme.FlashQuizTheme
@Composable
fun DeckDetailScreen(
    deck: Deck?,
    onAddCard: () -> Unit,
    onStartQuiz: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
){
    if (deck == null) {
        Text("Deck not found", modifier = modifier.padding(24.dp))
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp)) {
            Text(deck.name, style = MaterialTheme.typography.headlineMedium)
            Text("${deck.cards.size} cards", style = MaterialTheme.typography.bodyMedium)

            LazyColumn(
                modifier = Modifier.weight(1f).padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(deck.cards, key = { it.id }) { card ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            card.question,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = onAddCard, modifier = Modifier.weight(1f)) {
                    Text("Add card")
                }
                Button(
                    onClick = onStartQuiz,
                    enabled = deck.cards.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                ) { Text("Start quiz") }
            }
            Button(onClick = onBack,modifier = Modifier.navigationBarsPadding()) { Text("Back") }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeckDetailScreenPreview() {
    FlashQuizTheme {
        DeckDetailScreen(
            deck = Deck(
                1,
                "Kotlin basics",
                listOf(
                    Flashcard(1, "Q1?", "A"),
                    Flashcard(2,"Q2?","A"),
                    Flashcard(3,"Q2?","A"),
                    Flashcard(4,"Q2?","A"),
                    Flashcard(5,"Q2?","A"),
                    Flashcard(6,"Q2?","A"),
                    Flashcard(7,"Q2?","A"),
                    Flashcard(8,"Q2?","A"),
                    Flashcard(9,"Q2?","A"),
                    Flashcard(10,"Q2?","A"),
                    Flashcard(11,"Q2?","A"),
                    Flashcard(12,"Q2?","A"),
                    Flashcard(13,"Q2?","A"),

                )
            ),
            onBack = {},
            onAddCard = {},
            onStartQuiz = {}

        )
    }
}