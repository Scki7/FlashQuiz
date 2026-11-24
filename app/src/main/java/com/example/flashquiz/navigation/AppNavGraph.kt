package com.example.flashquiz.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flashquiz.ui.screens.AddCardScreen
import com.example.flashquiz.ui.screens.DeckDetailScreen
import com.example.flashquiz.ui.screens.DeckListScreen
import com.example.flashquiz.ui.screens.FlashcardScreen
import com.example.flashquiz.ui.screens.QuizScreen
import com.example.flashquiz.ui.screens.ResultScreen
import com.example.flashquiz.viewmodel.FlashQuizViewModel
import com.example.flashquiz.ui.screens.LearnScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: FlashQuizViewModel = viewModel()
) {
    val decks by viewModel.decks.collectAsState()
    val quizState by viewModel.quizState.collectAsState()
    val flashcardState by viewModel.flashcardState.collectAsState()
    val learnState by viewModel.learnState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = Screen.DeckList.route
    ) {

        composable(Screen.DeckList.route) {
            DeckListScreen(
                decks = decks,
                onDeckClick = { id ->
                    navController.navigate(Screen.DeckDetail.createRoute(id))
                },
                onAddDeck = { name -> viewModel.addDeck(name) }
            )
        }

        composable(
            route = Screen.DeckDetail.route,
            arguments = listOf(navArgument("deckId") { type = NavType.IntType })
        ) { entry ->
            val deckId = entry.arguments?.getInt("deckId") ?: -1
            val deck = decks.find { it.id == deckId }

            DeckDetailScreen(
                deck = deck,
                onAddCard = {
                    navController.navigate(Screen.AddCard.createRoute(deckId))
                },
                onStudy = {
                    viewModel.startFlashcards(deckId)
                    navController.navigate(Screen.Flashcards.createRoute(deckId))
                },
                onStartQuiz = {
                    viewModel.startQuiz(deckId)
                    navController.navigate(Screen.Quiz.createRoute(deckId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.AddCard.route,
            arguments = listOf(navArgument("deckId") { type = NavType.IntType })
        ) { entry ->
            val deckId = entry.arguments?.getInt("deckId") ?: -1

            AddCardScreen(
                onSave = { question, answer, wrongAnswers ->
                    viewModel.addCard(
                        deckId = deckId,
                        question = question,
                        answer = answer,
                        wrongAnswers = wrongAnswers
                    )
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
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

            QuizScreen(
                state = quizState,
                onSubmit = { viewModel.submitAnswer(it) },
                onNext = { viewModel.nextQuestion() },
                onBack = { navController.popBackStack() }
            )
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

            ResultScreen(
                score = score,
                total = total,
                onTryAgain = {
                    viewModel.startQuiz(deckId)
                    navController.navigate(Screen.Quiz.createRoute(deckId)) {
                        popUpTo(Screen.Result.route) { inclusive = true }
                    }
                },
                onBackToDecks = {
                    navController.popBackStack(Screen.DeckList.route, inclusive = false)
                }
            )
        }

        composable(
            route = Screen.Flashcards.route,
            arguments = listOf(navArgument("deckId") { type = NavType.IntType })
        ) {
            FlashcardScreen(
                state = flashcardState,
                onFlip = { viewModel.flipCard() },
                onNext = { viewModel.nextCard() },
                onPrevious = { viewModel.previousCard() },
                onToggleKnown = { viewModel.toggleKnown() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Learn.route,
            arguments = listOf(
                navArgument("deckId") {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            val deckId = entry.arguments?.getInt("deckId") ?: -1

            LaunchedEffect(deckId) {
                if (deckId != -1) {
                    viewModel.startLearn(deckId)
                }
            }

            LearnScreen(
                state = learnState,
                onAnswerSelected = { answer ->
                    viewModel.submitLearnAnswer(answer)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}
