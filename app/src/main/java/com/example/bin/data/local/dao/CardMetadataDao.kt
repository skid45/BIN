package com.example.bin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bin.data.local.entities.CardMetadata
import kotlinx.coroutines.flow.Flow

@Dao
interface CardMetadataDao {

    @Query("SELECT * FROM card_metadata")
    fun getAllCardMetadata(): Flow<List<CardMetadata>>

    @Insert
    suspend fun insert(cardMetadata: CardMetadata)

    @Query("DELETE FROM card_metadata")
    suspend fun deleteAllCardMetadata()
}