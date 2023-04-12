package com.example.bin.di

import android.content.Context
import androidx.room.Room
import com.example.bin.data.local.AppDatabase
import com.example.bin.data.local.dao.CardMetadataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCardMetadataDao(appDatabase: AppDatabase): CardMetadataDao {
        return appDatabase.getCardMetadataDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "card_metadata.db"
        ).build()
    }
}