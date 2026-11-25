package com.example.flashquiz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {

    @Query("SELECT * FROM decks ORDER BY id DESC")
    fun getAllDecks(): Flow<List<DeckEntity>>

    @Query("SELECT * FROM decks WHERE id = :deckId")
    suspend fun getDeckById(deckId: Int): DeckEntity?

    @Insert
    suspend fun insertDeck(deck: DeckEntity): Long

    @Update
    suspend fun updateDeck(deck: DeckEntity)

    @Delete
    suspend fun deleteDeck(deck: DeckEntity)
}
