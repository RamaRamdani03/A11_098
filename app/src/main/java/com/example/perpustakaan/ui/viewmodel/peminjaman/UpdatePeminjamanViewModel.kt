package com.example.perpustakaan.ui.viewmodel.peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.repository.PeminjamanRepository
import com.example.perpustakaan.ui.view.peminjaman.DestinasiUpdatePeminjaman
import kotlinx.coroutines.launch

class UpdatePeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val peminjamanRepository: PeminjamanRepository
): ViewModel() {
    var updateUiState by mutableStateOf(InsertPeminjamanUiState())
        private set

    private val _idPeminjaman: Int = checkNotNull(savedStateHandle[DestinasiUpdatePeminjaman.id_peminjaman])

    init {
        viewModelScope.launch {
            val peminjaman = peminjamanRepository.getPeminjamanById(_idPeminjaman)
            updateUiState = peminjaman.data.toUiStatePeminjaman()
        }
    }

    fun updateInsertPeminjamanState(insertUiEvent: InsertPeminjamanUiEvent) {
        updateUiState = InsertPeminjamanUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePeminjaman() {
        viewModelScope.launch {
            try {
                peminjamanRepository.updatePeminjaman(
                    _idPeminjaman,
                    updateUiState.insertUiEvent.toPeminjaman()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
