# FlashQuiz

FlashQuiz is an Android flashcards and quiz app built with Kotlin and Jetpack Compose.
The user creates decks of flashcards (e.g. "Kotlin basics", "English words"),
adds cards to them and takes a quiz. The app counts the score and shows the result.

## Group members
- Yerassyl Taubayev: project setup, data models, ViewModel, state, navigation
- Aibergen Tolepbergen: UI screens, reusable composables, theme

## Main features
- Create decks of flashcards
- Add cards (question and answer) to a deck
- Take a quiz on a deck, one question at a time
- Score counting and result screen with "Try again" and "Back to decks"
- Navigation between 5 screens with arguments (deckId, score, total)

## Tech stack
- Kotlin, Jetpack Compose, Material Design 3
- MVVM architecture (ViewModel + StateFlow)
- Navigation Compose

## Project structure
- `data/model`: Flashcard, Deck, QuizUiState
- `viewmodel`: FlashQuizViewModel
- `navigation`: Screen routes, AppNavGraph
- `ui/screens`: app screens
- `ui/components`: reusable composables
- `ui/theme`: Material theme
