package com.example.perpustakaan.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.repository.AnggotaRepository
import com.example.perpustakaan.ui.view.anggota.DestinasiUpdateAnggota
import kotlinx.coroutines.launch

class UpdateAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggotaRepository: AnggotaRepository
): ViewModel() {
    var updateUiState by mutableStateOf(InsertAnggotaUiState())
        private set

    private val _idAnggota: Int = checkNotNull(savedStateHandle[DestinasiUpdateAnggota.id_anggota])

    init {
        viewModelScope.launch {
            updateUiState = anggotaRepository.getAnggotaById(_idAnggota)
                .data.toUiStateAnggota()
        }
    }

    fun updateInsertAnggotaState(insertUiEvent: InsertAnggotaUiEvent) {
        updateUiState = InsertAnggotaUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateAnggota() {
        viewModelScope.launch {
            try {
                anggotaRepository.updateAnggota(_idAnggota, updateUiState.insertUiEvent.toAnggota())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
