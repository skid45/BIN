package com.example.bin.data.network

import com.example.bin.data.local.entities.CardMetadata
import com.example.bin.utils.asCardMetadata
import javax.inject.Inject

class CardMetadataService @Inject constructor(private val cardMetadataApi: CardMetadataApi) {


    suspend fun getCardMetadata(bin: Int): CardMetadata {
        return cardMetadataApi.getCardMetadataByBIN(bin).asCardMetadata(bin)
    }

    companion object {
        const val BASE_URL = "https://lookup.binlist.net/"
    }
}