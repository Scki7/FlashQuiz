package com.example.flashquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        DeckEntity::class,
        FlashcardEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FlashQuizDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao

    abstract fun flashcardDao(): FlashcardDao
}
