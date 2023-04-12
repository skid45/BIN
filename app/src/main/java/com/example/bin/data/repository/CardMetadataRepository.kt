package com.example.bin.data.repository

import com.example.bin.data.local.dao.CardMetadataDao
import com.example.bin.data.local.entities.CardMetadata
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardMetadataRepository @Inject constructor(private val cardMetadataDao: CardMetadataDao) {

    val allCardMetadata: Flow<List<CardMetadata>> = cardMetadataDao.getAllCardMetadata()


    suspend fun insert(cardMetadata: CardMetadata) {
        cardMetadataDao.insert(cardMetadata)
    }

    suspend fun deleteAll() {
        cardMetadataDao.deleteAllCardMetadata()
    }
}