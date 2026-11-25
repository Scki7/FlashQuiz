package com.example.flashquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DeckEntity::class,
        FlashcardEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FlashQuizDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao

    abstract fun flashcardDao(): FlashcardDao
}
