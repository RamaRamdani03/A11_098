package com.example.perpustakaan.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.data.repository.AnggotaRepository
import kotlinx.coroutines.launch

class DetailAnggotaViewModel(private val anggotaRepository: AnggotaRepository) : ViewModel() {



    var uiState by mutableStateOf(DetailAnggotaUiState())
        private set

    fun fetchDetailAnggota(id_anggota: Int) {
        viewModelScope.launch {
            uiState = DetailAnggotaUiState(isLoading = true)
            try {
                val anggota = anggotaRepository.getAnggotaById(id_anggota)
                uiState = DetailAnggotaUiState(detailUiEvent = anggota.data.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailAnggotaUiState(
                    isError = true,
                    errrorMessage = "Failed to fetch anggota details: ${e.message}"
                )
            }
        }
    }
}

data class DetailAnggotaUiState(
    val detailUiEvent: InsertAnggotaUiEvent = InsertAnggotaUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errrorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertAnggotaUiEvent()
}


fun Anggota.toDetailUiEvent(): InsertAnggotaUiEvent {
    return InsertAnggotaUiEvent(
        id_anggota = id_anggota,
        nama = nama,
        email = email,
        nomor_telepon = nomor_telepon
    )
}