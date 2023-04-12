package com.example.bin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bin.data.local.dao.CardMetadataDao
import com.example.bin.data.local.entities.CardMetadata

@Database(entities = [CardMetadata::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCardMetadataDao(): CardMetadataDao

}