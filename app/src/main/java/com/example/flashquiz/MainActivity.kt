
package com.example.flashquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room

import com.example.flashquiz.data.local.FlashQuizDatabase
import com.example.flashquiz.data.repository.FlashQuizRepository
import com.example.flashquiz.navigation.AppNavGraph
import com.example.flashquiz.ui.theme.FlashQuizTheme
import com.example.flashquiz.viewmodel.FlashQuizViewModel
import com.example.flashquiz.viewmodel.FlashQuizViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val database = Room.databaseBuilder(
            applicationContext,
            FlashQuizDatabase::class.java,
            "flashquiz_database"
        ).build()

        val repository = FlashQuizRepository(
            deckDao = database.deckDao(),
            flashcardDao = database.flashcardDao()
        )

        val factory = FlashQuizViewModelFactory(repository)

        setContent {
            val flashQuizViewModel: FlashQuizViewModel = viewModel(
                factory = factory
            )

            FlashQuizTheme {
                AppNavGraph(
                    viewModel = flashQuizViewModel
                )
            }
        }
    }
}
