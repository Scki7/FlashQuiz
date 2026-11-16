package com.example.flashquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.flashquiz.navigation.AppNavGraph
import com.example.flashquiz.ui.theme.FlashQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlashQuizTheme {
                AppNavGraph()
            }
        }
    }
}