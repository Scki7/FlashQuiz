package com.example.flashquiz.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashquiz.data.model.Deck
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun DeckDetailScreen(
    deck: Deck?,
    onAddCard: () -> Unit,
    onStudy: () -> Unit,
    onStartLearn: () -> Unit,
    onStartQuiz: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onUpdateCard: (Int, Int, String, String, List<String>) -> Unit ,
    onDeleteCard: (Int) -> Unit
) {
    if (deck == null) {
        Text("Deck not found", modifier = modifier.padding(24.dp))
        return
    }
    // Edit and Delete card state
    var editingCardId by rememberSaveable { mutableStateOf<Int?>(null) }
    var deletingCardId by rememberSaveable { mutableStateOf<Int?>(null) }

    var editedQuestion by rememberSaveable { mutableStateOf("") }
    var editedAnswer by rememberSaveable { mutableStateOf("") }
    var editedWrongAnswers by rememberSaveable {
        mutableStateOf(listOf("", "", ""))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            FilledTonalButton(onClick = onAddCard) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Add card")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = deck.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${deck.cards.size} cards",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        // Cards section
        Text(
            text = "Cards",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = deck.cards,
                key = { _, card -> card.id }
            ) { index, card ->
                var expanded by remember(card.id) {
                    mutableStateOf(false)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${index + 1}.",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.width(12.dp))

                            Text(
                                text = card.question,
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null
                            )
                        }

                        if (expanded) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp)
                            )

                            Text(
                                text = card.answer,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        // Edit and Delete buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    editingCardId = card.id
                                    editedQuestion = card.question
                                    editedAnswer = card.answer
                                    editedWrongAnswers = List(3) { index ->
                                        card.wrongAnswers.getOrElse(index) { "" }
                                    }
                                }
                            ) {
                                Text("Edit")
                            }

                            TextButton(
                                onClick = {
                                    deletingCardId = card.id
                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Study modes
        Text(
            text = "Study modes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StudyModeButton(
                title = "Flashcards",
                description = "Review cards and reveal answers",
                icon = {
                    Icon(Icons.Default.Layers, contentDescription = null)
                },
                onClick = onStudy,
                enabled = deck.cards.isNotEmpty()
            )

            // Learn mode will be added here later.
            StudyModeButton(
                title = "Learn",
                description = "Practice with multiple-choice questions",
                icon = {
                    Icon(
                        Icons.Default.School,
                        contentDescription = null
                    )
                },
                onClick = onStartLearn,
                enabled = deck.cards.isNotEmpty()
            )

            StudyModeButton(
                title = "Test",
                description = "Check your knowledge",
                icon = {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                },
                onClick = onStartQuiz,
                enabled = deck.cards.isNotEmpty()
            )
        }

        // Edit card dialog
        if (editingCardId != null) {
            AlertDialog(
                onDismissRequest = { editingCardId = null },
                title = { Text("Edit card") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = editedQuestion,
                            onValueChange = { editedQuestion = it },
                            label = { Text("Question") }
                        )

                        OutlinedTextField(
                            value = editedAnswer,
                            onValueChange = { editedAnswer = it },
                            label = { Text("Correct answer") }
                        )

                        editedWrongAnswers.forEachIndexed { index, answer ->
                            OutlinedTextField(
                                value = answer,
                                onValueChange = { newValue ->
                                    editedWrongAnswers =
                                        editedWrongAnswers.toMutableList().also {
                                            it[index] = newValue
                                        }
                                },
                                label = {
                                    Text("Wrong answer ${index + 1}")
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            editingCardId?.let { cardId ->
                                onUpdateCard(
                                    cardId,
                                    deck.id,
                                    editedQuestion,
                                    editedAnswer,
                                    editedWrongAnswers
                                )
                            }
                            editingCardId = null
                        },
                        enabled = editedQuestion.isNotBlank() &&
                                editedAnswer.isNotBlank()
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { editingCardId = null }) {
                        Text("Cancel")
                    }
                }
            )
        }

    }

    // Delete card dialog
    if (deletingCardId != null) {
        AlertDialog(
            onDismissRequest = { deletingCardId = null },
            title = { Text("Delete card?") },
            text = {
                Text("Are you sure you want to delete this card?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        deletingCardId?.let { cardId ->
                            onDeleteCard(cardId)
                        }
                        deletingCardId = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingCardId = null }) {
                    Text("Cancel")
                }
            }
        )
    }

}

@Composable
private fun StudyModeButton(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    enabled: Boolean
) {
    OutlinedCard(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            icon()

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null
            )
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
                    Flashcard(1, "Keyword for a read-only variable?", "val"),
                    Flashcard(2, "Keyword for a mutable variable?", "var"),
                    Flashcard(3, "Keyword to declare a function?", "fun")
                )
            ),
            onAddCard = {},
            onStudy = {},
            onStartLearn = {},
            onStartQuiz = {},
            onBack = {},
            onUpdateCard =  { _, _, _, _, _ -> },
            onDeleteCard = {}
        )
    }
}
