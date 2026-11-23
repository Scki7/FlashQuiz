package com.example.flashquiz.navigation

sealed class Screen(val route: String) {
    object DeckList : Screen("deck_list")
    object DeckDetail : Screen("deck_detail/{deckId}") {
        fun createRoute(deckId: Int) = "deck_detail/$deckId" }

    object AddCard : Screen("add_card/{deckId}") {
        fun createRoute(deckId: Int) = "add_card/$deckId" }

    object Quiz : Screen("quiz/{deckId}") {
        fun createRoute(deckId: Int) = "quiz/$deckId" }

    object Study : Screen("study/{deckId}") {
        fun createRoute(deckId: Int) = "study/$deckId"
    }
    object Result : Screen("result/{score}/{total}/{deckId}") {
        fun createRoute(score: Int, total: Int, deckId: Int) =
            "result/$score/$total/$deckId" }
}