package com.example.flashquiz.navigation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val quizState by viewModel.quizState.collectAsState()

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
                Button(onClick = {
                    navController.navigate(Screen.AddCard.createRoute(deckId))
                }) { Text("Add card") }

                Button(
                    enabled = deck?.cards?.isNotEmpty() == true,
                    onClick = {
                        viewModel.startQuiz(deckId)
                        navController.navigate(Screen.Quiz.createRoute(deckId))
                    }
                ) { Text("Start quiz") }

                Button(onClick = { navController.popBackStack() }) { Text("Back") }
            }
        }

        composable(
            route = Screen.AddCard.route,
            arguments = listOf(navArgument("deckId") { type = NavType.IntType })
        ) { entry ->
            val deckId = entry.arguments?.getInt("deckId") ?: -1

            Placeholder("Add card to deck $deckId") {
                Button(onClick = {
                    viewModel.addCard(deckId, "Test question", "test")
                    navController.popBackStack()
                }) { Text("Save test card") }

                Button(onClick = { navController.popBackStack() }) { Text("Back") }
            }
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(navArgument("deckId") { type = NavType.IntType })
        ) { entry ->
            val deckId = entry.arguments?.getInt("deckId") ?: -1

            LaunchedEffect(quizState.isFinished) {
                if (quizState.isFinished) {
                    navController.navigate(
                        Screen.Result.createRoute(quizState.score, quizState.total, deckId)
                    ) {
                        popUpTo(Screen.Quiz.route) { inclusive = true }
                    }
                }
            }

            Placeholder("Q ${quizState.currentIndex + 1}/${quizState.total}: ${quizState.currentCard?.question}") {
                Text("Score: ${quizState.score}")
                Text("Last answer correct: ${quizState.lastAnswerCorrect}")

                Button(onClick = {
                    viewModel.submitAnswer(quizState.currentCard?.answer ?: "")
                }) { Text("Answer correctly (test)") }

                Button(onClick = { viewModel.nextQuestion() }) { Text("Next") }

                Button(onClick = { navController.popBackStack() }) { Text("Back") }
            }
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("deckId") { type = NavType.IntType }
            )
        ) { entry ->
            val score = entry.arguments?.getInt("score") ?: 0
            val total = entry.arguments?.getInt("total") ?: 0
            val deckId = entry.arguments?.getInt("deckId") ?: -1

            Placeholder("Result: $score / $total") {
                Button(onClick = {
                    viewModel.startQuiz(deckId)
                    navController.navigate(Screen.Quiz.createRoute(deckId)) {
                        popUpTo(Screen.Result.route) { inclusive = true }
                    }
                }) { Text("Try again") }

                Button(onClick = {
                    navController.popBackStack(Screen.DeckList.route, inclusive = false)
                }) { Text("Back to decks") }
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