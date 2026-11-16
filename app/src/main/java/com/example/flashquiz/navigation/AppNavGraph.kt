package com.example.flashquiz.navigation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flashquiz.viewmodel.FlashQuizViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: FlashQuizViewModel = viewModel()
) {
    val decks by viewModel.decks.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.DeckList.route
    ) {

        composable(Screen.DeckList.route) {

            Placeholder("Deck list") {
                decks.forEach { deck ->
                    Button(onClick = {
                        navController.navigate(Screen.DeckDetail.createRoute(deck.id))
                    }) { Text("${deck.name} (${deck.cards.size})") }
                }

                Button(onClick = { viewModel.addDeck("New deck ${decks.size + 1}") }) {
                    Text("Add deck")
                }
            }
        }

        composable(
            route = Screen.DeckDetail.route,
            arguments = listOf(navArgument("deckId") { type = NavType.IntType })
        ) { entry ->
            val deckId = entry.arguments?.getInt("deckId") ?: -1
            val deck = decks.find { it.id == deckId }

            Placeholder("Deck: ${deck?.name} (${deck?.cards?.size} cards)") {
                Button(onClick = { navController.popBackStack() }) { Text("Back") }
            }
        }
    }
}

@Composable
private fun Placeholder(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(title)

        content()
    }
}