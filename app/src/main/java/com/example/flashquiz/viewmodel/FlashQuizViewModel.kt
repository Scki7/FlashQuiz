package com.example.flashquiz.viewmodel
import androidx.lifecycle.ViewModel
import com.example.flashquiz.data.model.Deck
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.model.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlashQuizViewModel : ViewModel() {

    private var nextDeckId = 3
    private var nextCardId = 100
    private val _decks = MutableStateFlow(sampleDecks())
    val decks: StateFlow<List<Deck>> = _decks.asStateFlow()
    private val _quizState = MutableStateFlow(QuizUiState())
    val quizState: StateFlow<QuizUiState> = _quizState.asStateFlow()





    fun getDeck(deckId: Int): Deck? = _decks.value.find { it.id == deckId }
    fun addDeck(name: String) {
        if (name.isBlank()) return

        _decks.update { it + Deck(id = nextDeckId++, name = name.trim()) }
    }

    fun addCard(deckId: Int, question: String, answer: String) {
        if (question.isBlank() || answer.isBlank()) return
        val card = Flashcard(nextCardId++, question.trim(), answer.trim())
        _decks.update { list ->
            list.map { deck ->
                if (deck.id == deckId) deck.copy(cards = deck.cards + card) else deck
            }
        }
    }


    fun startQuiz(deckId: Int) {
        val cards = getDeck(deckId)?.cards.orEmpty().shuffled()
        _quizState.value = QuizUiState(deckId = deckId, cards = cards)
    }




    fun submitAnswer(answer: String) {


        _quizState.update { state ->
            val card = state.currentCard
            if (card == null || state.isFinished || state.lastAnswerCorrect != null) {
                state
            } else {


                val correct = answer.trim().equals(card.answer, ignoreCase = true)
                state.copy(

                    score = if (correct) state.score + 1 else state.score,
                    lastAnswerCorrect = correct)

            }
        } }



    fun nextQuestion() {
        _quizState.update { state ->
            val next = state.currentIndex + 1
            if (next >= state.cards.size) {


                state.copy(isFinished = true)
            } else {
                state.copy(currentIndex = next, lastAnswerCorrect = null)
            }
        }
    }

    private fun sampleDecks() = listOf(

        Deck(
            id = 1, name = "Kotlin basics",
            cards = listOf(
                Flashcard(1, "Keyword for a read-only variable?", "val"),
                Flashcard(2, "Keyword for a mutable variable?", "var"),

                Flashcard(3, "Keyword to declare a function?", "fun")
            )
        )
        ,

        Deck(
            id = 2, name = "English words",
            cards = listOf(
                Flashcard(4, "Translate: яблоко", "apple"),
                Flashcard(5, "Translate: книга", "book")
            )
        )
    )
}