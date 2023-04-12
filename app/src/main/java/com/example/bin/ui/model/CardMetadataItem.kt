package com.example.bin.ui.model

import android.text.Spannable

data class CardMetadataItem(
    val id: Int,
    val bin: String,
    val scheme: String,
    val brand: String,
    val length: String,
    val luhn: String,
    val type: String,
    val prepaid: String,
    val country: String,
    val coordinates: Spannable,
    val latitude: Double?,
    val longitude: Double?,
    val bankName: String,
    val city: String,
    val url: String,
    val phone: String,
)
