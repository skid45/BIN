package com.example.bin.data.network

import com.example.bin.data.network.model.CardMetadataFromJSON
import retrofit2.http.GET
import retrofit2.http.Path

interface CardMetadataApi {
    @GET("{bin}")
    suspend fun getCardMetadataByBIN(@Path("bin") bin: Int): CardMetadataFromJSON
}