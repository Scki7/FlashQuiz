package com.example.flashquiz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun DeckCard(modifier: Modifier = Modifier) {
    Text(text = "Flash Quiz App", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun DeckCardPreview() {
    FlashQuizTheme {
        DeckCard()
    }
}