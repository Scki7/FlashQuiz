package com.example.flashquiz.viewmodel
import androidx.lifecycle.ViewModel
import com.example.flashquiz.data.model.Deck
import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.model.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.flashquiz.data.model.FlashcardUiState
import com.example.flashquiz.data.model.LearnUiState
import androidx.lifecycle.viewModelScope
import com.example.flashquiz.data.repository.FlashQuizRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.ExperimentalCoroutinesApi
@OptIn(ExperimentalCoroutinesApi::class)
class FlashQuizViewModel(
    private val repository: FlashQuizRepository
) : ViewModel() {

    private val _decks = MutableStateFlow<List<Deck>>(emptyList())
    val decks: StateFlow<List<Deck>> = _decks.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllDecks()
                .flatMapLatest { deckEntities ->

                    if (deckEntities.isEmpty()) {
                        flowOf(emptyList<Deck>())
                    } else {
                        combine(
                            deckEntities.map { deckEntity ->
                                repository.getCardsByDeck(deckEntity.id)
                            }
                        ) { cardLists ->

                            deckEntities.mapIndexed { index, deckEntity ->
                                Deck(
                                    id = deckEntity.id,
                                    name = deckEntity.name,
                                    cards = cardLists[index].map { card ->
                                        Flashcard(
                                            id = card.id,
                                            question = card.question,
                                            answer = card.answer,
                                            wrongAnswers = card.wrongAnswers
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                .collectLatest { loadedDecks ->
                    _decks.value = loadedDecks
                }
        }
    }

    private val _quizState = MutableStateFlow(QuizUiState())
    val quizState: StateFlow<QuizUiState> = _quizState.asStateFlow()
    private val _flashcardState = MutableStateFlow(FlashcardUiState())
    val flashcardState: StateFlow<FlashcardUiState> = _flashcardState.asStateFlow()
    private val _learnState = MutableStateFlow(LearnUiState())
    val learnState: StateFlow<LearnUiState> = _learnState.asStateFlow()
    fun getDeck(deckId: Int): Deck? = _decks.value.find { it.id == deckId }
    fun addDeck(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            repository.addDeck(name.trim())
        }
    }
    fun addCard(
        deckId: Int,
        question: String,
        answer: String,
        wrongAnswers: List<String>
    ) {
        if (question.isBlank() || answer.isBlank()) return

        viewModelScope.launch {
            repository.addCard(
                deckId = deckId,
                question = question.trim(),
                answer = answer.trim(),
                wrongAnswers = wrongAnswers
            )
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
    fun startFlashcards(deckId: Int) {
        val cards = getDeck(deckId)?.cards.orEmpty()
        _flashcardState.value = FlashcardUiState(deckId = deckId, cards = cards)
    }
    private fun generateAnswerOptions(
        card: Flashcard,
        deckCards: List<Flashcard>
    ): List<String> {

        val correctAnswer = card.answer.trim()

        val manualOptions = card.wrongAnswers
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .filter { !it.equals(correctAnswer, ignoreCase = true) }
            .distinctBy { it.lowercase() }
            .take(3)

        val otherOptions = deckCards
            .filter { it.id != card.id }
            .map { it.answer.trim() }
            .filter { it.isNotBlank() }
            .filter { !it.equals(correctAnswer, ignoreCase = true) }
            .filter { candidate ->
                manualOptions.none {
                    it.equals(candidate, ignoreCase = true)
                }
            }
            .distinctBy { it.lowercase() }
            .shuffled()

        val wrongOptions = (manualOptions + otherOptions)
            .distinctBy { it.lowercase() }
            .take(3)

        return (wrongOptions + correctAnswer).shuffled()
    }
    fun startLearn(deckId: Int) {
        val cards = getDeck(deckId)?.cards.orEmpty().shuffled()

        val options = cards.firstOrNull()?.let { card ->
            generateAnswerOptions(card, cards)
        }.orEmpty()

        _learnState.value = LearnUiState(
            deckId = deckId,
            cards = cards,
            originalTotal = cards.size,
            answerOptions = options,
            isFinished = cards.isEmpty()
        )
    }
    fun submitLearnAnswer(answer: String) {
        _learnState.update { state ->
            val card = state.currentCard

            if (
                card == null ||
                state.isFinished ||
                state.selectedAnswer != null ||
                answer !in state.answerOptions
            ) {
                state
            } else {
                val isCorrect = answer.trim()
                    .equals(card.answer.trim(), ignoreCase = true)
                val updatedRetryCards = if (isCorrect) {
                    state.retryCards
                } else {
                    state.retryCards + card
                }
                state.copy(
                    selectedAnswer = answer,
                    correctAnswers = state.correctAnswers +
                            if (isCorrect) 1 else 0,
                    wrongAnswers = state.wrongAnswers +
                            if (isCorrect) 0 else 1,
                    retryCards = updatedRetryCards
                )
            }
        }
    }

    fun nextLearnQuestion() {
        _learnState.update { state ->

            if (state.isFinished || state.selectedAnswer == null) {
                state
            } else {
                val nextIndex = state.currentIndex + 1

                if (nextIndex < state.cards.size) {
                    val nextCard = state.cards[nextIndex]

                    state.copy(
                        currentIndex = nextIndex,
                        selectedAnswer = null,
                        answerOptions = generateAnswerOptions(
                            card = nextCard,
                            deckCards = state.cards
                        )
                    )
                } else if (state.retryCards.isNotEmpty()) {

                    val retryCards = state.retryCards.shuffled()
                    val nextCard = retryCards.first()

                    state.copy(
                        cards = retryCards,
                        retryCards = emptyList(),
                        currentIndex = 0,
                        selectedAnswer = null,
                        answerOptions = generateAnswerOptions(
                            card = nextCard,
                            deckCards = state.cards
                        )
                    )
                } else {
                    state.copy(
                        currentIndex = state.cards.size,
                        selectedAnswer = null,
                        answerOptions = emptyList(),
                        isFinished = true
                    )
                }
            }
        }
    }



    fun flipCard() {
        _flashcardState.update { state -> state.copy(isFlipped = !state.isFlipped) }
    }
    fun nextCard() {
        _flashcardState.update { state ->
            if (state.isLast) {
                state
            } else {
                state.copy(currentIndex = state.currentIndex + 1, isFlipped = false)
            }
        }
    }
    fun previousCard() {
        _flashcardState.update { state ->
            if (state.isFirst) {
                state
            } else {
                state.copy(currentIndex = state.currentIndex - 1, isFlipped = false)
            }
        }
    }

    fun toggleKnown() {
        _flashcardState.update { state ->
            val card = state.currentCard
            if (card == null) {
                state
            } else if (card.id in state.knownIds) {
                state.copy(knownIds = state.knownIds - card.id)
            } else {
                state.copy(knownIds = state.knownIds + card.id)
            }
        }
    }
    fun shuffleFlashcards() {
        _flashcardState.update { state ->
            state.copy(cards = state.cards.shuffled(), currentIndex = 0, isFlipped = false)
        }
    }
}