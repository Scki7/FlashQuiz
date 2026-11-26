package com.example.flashquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.model.Deck
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.ui.components.DeckCard
import com.example.flashquiz.ui.theme.FlashQuizTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme

@Composable
fun DeckListScreen(
    decks: List<Deck>,
    onDeckClick: (Int) -> Unit,
    onAddDeck: (String) -> Unit,
    modifier: Modifier = Modifier,
    onUpdateDeck: (Int, String) -> Unit,
    onDeleteDeck: (Int) -> Unit,
    onSettingsClick: () -> Unit = {}
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var newName by rememberSaveable { mutableStateOf("") }

    // Edit and Delete state
    var editingDeckId by rememberSaveable { mutableStateOf<Int?>(null) }
    var editedName by rememberSaveable { mutableStateOf("") }
    var deletingDeckId by rememberSaveable { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onSettingsClick) {
                Text("Settings")
            }
        }
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

                // Edit and Delete buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            editingDeckId = deck.id
                            editedName = deck.name
                        }
                    ) {
                        Text("Edit")
                    }

                    TextButton(
                        onClick = {
                            deletingDeckId = deck.id
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            Text("Add deck")
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("New deck") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Deck name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAddDeck(newName)
                        newName = ""
                        showDialog = false
                    },
                    enabled = newName.isNotBlank()
                ) { Text("Create") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Edit deck dialog
    if (editingDeckId != null) {
        AlertDialog(
            onDismissRequest = { editingDeckId = null },
            title = { Text("Edit deck") },
            text = {
                OutlinedTextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    label = { Text("Deck name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        editingDeckId?.let { id ->
                            onUpdateDeck(id, editedName)
                        }
                        editingDeckId = null
                    },
                    enabled = editedName.isNotBlank()
                ) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { editingDeckId = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Delete confirmation dialog
    if (deletingDeckId != null) {
        AlertDialog(
            onDismissRequest = { deletingDeckId = null },
            title = { Text("Delete deck?") },
            text = {
                Text("This deck and all its flashcards will be deleted.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        deletingDeckId?.let { id ->
                            onDeleteDeck(id)
                        }
                        deletingDeckId = null
                    }
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { deletingDeckId = null }) {
                    Text("Cancel")
                }
            }
        )
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
            onAddDeck = {},
            onUpdateDeck =  { _, _ -> },
            onDeleteDeck = {}
        )
    }
}
