package com.example.flashquiz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Query("SELECT * FROM flashcards WHERE deckId = :deckId ORDER BY id ASC")
    fun getCardsByDeck(deckId: Int): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE id = :cardId")
    suspend fun getCardById(cardId: Int): FlashcardEntity?

    @Insert
    suspend fun insertCard(card: FlashcardEntity): Long

    @Update
    suspend fun updateCard(card: FlashcardEntity)

    @Delete
    suspend fun deleteCard(card: FlashcardEntity)
}
