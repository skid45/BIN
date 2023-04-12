package com.example.bin.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardMetadataFromJSON(

    val number: Number?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country?,
    val bank: Bank?,
)


@JsonClass(generateAdapter = true)
data class Number(
    val length: Int?,
    val luhn: Boolean?,
)


@JsonClass(generateAdapter = true)
data class Country(
    val name: String?,
    val latitude: Double?,
    val longitude: Double?,
)


@JsonClass(generateAdapter = true)
data class Bank(
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?,
)
