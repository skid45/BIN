package com.example.bin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CardMetadata::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCardMetadataDao(): CardMetadataDao

    companion object {
        fun getDB(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "card_metadata.db"
            ).build()
        }
    }

}