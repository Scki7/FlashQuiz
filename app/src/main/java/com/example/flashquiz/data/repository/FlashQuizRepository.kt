package com.example.flashquiz.data.repository

import com.example.flashquiz.data.local.DeckDao
import com.example.flashquiz.data.local.DeckEntity
import com.example.flashquiz.data.local.FlashcardDao
import com.example.flashquiz.data.local.FlashcardEntity
import kotlinx.coroutines.flow.Flow

class FlashQuizRepository(
    private val deckDao: DeckDao,
    private val flashcardDao: FlashcardDao
) {
    // DECK OPERATIONS
    fun getAllDecks(): Flow<List<DeckEntity>> {
        return deckDao.getAllDecks()
    }
    suspend fun getDeckById(id: Int): DeckEntity? {
        return deckDao.getDeckById(id)
    }
    suspend fun addDeck(name: String): Long {
        return deckDao.insertDeck(
            DeckEntity(name = name)
        )
    }
    suspend fun updateDeck(deck: DeckEntity) {
        deckDao.updateDeck(deck)
    }
    suspend fun deleteDeck(deck: DeckEntity) {
        deckDao.deleteDeck(deck)
    }
    // FLASHCARD OPERATIONS
    fun getCardsByDeck(
        deckId: Int
    ): Flow<List<FlashcardEntity>> {
        return flashcardDao.getCardsByDeck(deckId)
    }
    suspend fun getCardById(
        id: Int
    ): FlashcardEntity? {
        return flashcardDao.getCardById(id)
    }
    suspend fun addCard(
        deckId: Int,
        question: String,
        answer: String
    ): Long {
        return flashcardDao.insertCard(
            FlashcardEntity(
                deckId = deckId,
                question = question,
                answer = answer
            )
        )
    }
    suspend fun updateCard(card: FlashcardEntity) {
        flashcardDao.updateCard(card)
    }
    suspend fun deleteCard(card: FlashcardEntity) {
        flashcardDao.deleteCard(card)
    }
}
