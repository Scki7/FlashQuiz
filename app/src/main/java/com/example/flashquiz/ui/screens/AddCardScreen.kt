package com.example.flashquiz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.ui.theme.FlashQuizTheme
@Composable
fun AddCardScreen(
    onSave: (String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier= Modifier
){
    var question by rememberSaveable{mutableStateOf("")}
    var answer by rememberSaveable() { mutableStateOf("")}

    Column(
        modifier= modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Add card", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = question,
            onValueChange = {question=it},
            label={Text("Question")},
            modifier= Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value=answer,
            onValueChange = {answer=it},
            label={Text("Answer")},
            modifier= Modifier.fillMaxSize()
        )
        Button(
            onClick = { onSave(question, answer) },
            enabled = question.isNotBlank() && answer.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Save") }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AddCardScreenPreview() {
    FlashQuizTheme { AddCardScreen(onSave = { _, _ -> }, onBack = {}) }
}