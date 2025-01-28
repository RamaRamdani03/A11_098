package com.example.perpustakaan.ui.viewmodel.pengembalian

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.repository.PengembalianRepository
import com.example.perpustakaan.ui.view.pengembalian.DestinasiUpdatePengembalian
import kotlinx.coroutines.launch

class UpdatePengembalianViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengembalianRepository: PengembalianRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(InsertPengembalianUiState())
        private set

    private val _idPengembalian: Int = checkNotNull(savedStateHandle[DestinasiUpdatePengembalian.id_pengembalian])

    init {
        viewModelScope.launch {
            try {
                val pengembalian = pengembalianRepository.getPengembalianById(_idPengembalian)
                updateUiState = pengembalian.data.toUiStatePengembalian()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPengembalianState(insertUiEvent: InsertPengembalianUiEvent) {
        updateUiState = InsertPengembalianUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePengembalian() {
        viewModelScope.launch {
            try {
                pengembalianRepository.updatePengembalian(
                    _idPengembalian,
                    updateUiState.insertUiEvent.toPengembalian()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
