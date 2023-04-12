package com.example.bin.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bin.data.local.entities.CardMetadata
import com.example.bin.data.network.CardMetadataService
import com.example.bin.data.repository.CardMetadataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class CardMetadataViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val cardMetadataRepository: CardMetadataRepository,
    private val cardMetadataService: CardMetadataService,
) : ViewModel() {

    val cardMetadataDataset: Flow<List<CardMetadata>> = cardMetadataRepository.allCardMetadata


    fun requestCardMetadataByBIN(bin: Int) {
        viewModelScope.launch {
            try {
                cardMetadataRepository.insert(cardMetadataService.getCardMetadata(bin))
            } catch (e: HttpException) {
                Toast.makeText(
                    applicationContext,
                    "BIN: \"$bin\" is not valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            cardMetadataRepository.deleteAll()
        }
    }

}