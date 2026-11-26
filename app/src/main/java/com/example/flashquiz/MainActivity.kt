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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flashquiz.data.local.ThemePreferences
import kotlinx.coroutines.launch
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
        val themePreferences = ThemePreferences(applicationContext)


        setContent {
            val flashQuizViewModel: FlashQuizViewModel = viewModel(
                factory = factory
            )

            val isDarkTheme by themePreferences.isDarkTheme.collectAsState(
                initial = false
            )

            val scope = rememberCoroutineScope()

            FlashQuizTheme(darkTheme = isDarkTheme) {
                AppNavGraph(
                    viewModel = flashQuizViewModel,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { enabled ->
                        scope.launch {
                            themePreferences.saveTheme(enabled)
                        }
                    }
                )
            }
        }
    }
}
