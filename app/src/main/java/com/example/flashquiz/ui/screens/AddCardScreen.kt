
package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun AddCardScreen(
    onSave: (String, String, List<String>) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var question by rememberSaveable { mutableStateOf("") }
    var answer by rememberSaveable { mutableStateOf("") }

    var wrongAnswer1 by rememberSaveable { mutableStateOf("") }
    var wrongAnswer2 by rememberSaveable { mutableStateOf("") }
    var wrongAnswer3 by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        Text(
            text = "Add card",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Create a new flashcard for your deck.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Card information",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text("Question") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                OutlinedTextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text("Correct answer") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Other answer options (optional)",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Add up to 3 incorrect answers for Learn mode.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = wrongAnswer1,
                    onValueChange = { wrongAnswer1 = it },
                    label = { Text("Wrong answer 1") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = wrongAnswer2,
                    onValueChange = { wrongAnswer2 = it },
                    label = { Text("Wrong answer 2") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = wrongAnswer3,
                    onValueChange = { wrongAnswer3 = it },
                    label = { Text("Wrong answer 3") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Missing options will be filled using answers from other cards.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Button(
            onClick = {
                val wrongAnswers = listOf(
                    wrongAnswer1,
                    wrongAnswer2,
                    wrongAnswer3
                )
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                    .distinctBy { it.lowercase() }
                    .filter { !it.equals(answer.trim(), ignoreCase = true) }

                onSave(
                    question.trim(),
                    answer.trim(),
                    wrongAnswers
                )
            },
            enabled = question.isNotBlank() && answer.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Save card")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCardScreenPreview() {
    FlashQuizTheme {
        AddCardScreen(
            onSave = { _, _, _ -> },
            onBack = {}
        )
    }
}
