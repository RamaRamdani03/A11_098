package com.example.perpustakaan.ui.viewmodel.buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.repository.BukuRepository
import com.example.perpustakaan.ui.view.buku.DestinasiUpdateBuku
import kotlinx.coroutines.launch

class UpdateBukuViewModel (
    savedStateHandle: SavedStateHandle,
    private val bukuRepository: BukuRepository
): ViewModel() {
    var updateUiState by mutableStateOf(InsertBukuUiState())
        private set

    private val _idBuku: Int = checkNotNull(savedStateHandle[DestinasiUpdateBuku.id_buku])

    init {
        viewModelScope.launch {
            updateUiState = bukuRepository.getBukuById(_idBuku)
                .data.toUiStateBuku()
        }
    }

    fun updateInsertBukuState(insertUiEvent: InsertBukuUiEvent) {
        updateUiState = InsertBukuUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateBuku() {
        viewModelScope.launch {
            try {
                bukuRepository.updateBuku(_idBuku, updateUiState.insertUiEvent.toBuku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}