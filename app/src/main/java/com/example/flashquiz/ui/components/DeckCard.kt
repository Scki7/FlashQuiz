package com.example.flashquiz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun DeckCard(
    name : String,
    cardCount:Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,

) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column(modifier= Modifier.padding(6.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$cardCount cards",
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DeckCardPreview() {
    FlashQuizTheme {
        DeckCard(name = "Kotlin basics", cardCount = 3, onClick = { })
    }
}