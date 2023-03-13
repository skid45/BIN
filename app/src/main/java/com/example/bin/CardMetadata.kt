package com.example.bin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_metadata")
data class CardMetadata(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val bin: Int? = null,
    val scheme: String? = null,
    val brand: String? = null,
    val length: Int? = null,
    val luhn: Boolean? = null,
    val type: String? = null,
    val prepaid: Boolean? = null,
    val country: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null,
    val bankName: String? = null,
    val city: String? = null,
    val bankURL: String? = null,
    val bankPhoneNumber: String? = null
) {
    fun booleanToString(boolean: Boolean?): String {
        return when (boolean) {
            true -> "Yes"
            false -> "No"
            else -> ""
        }
    }
}
