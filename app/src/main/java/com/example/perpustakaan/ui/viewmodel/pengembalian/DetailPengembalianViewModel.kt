package com.example.perpustakaan.ui.viewmodel.pengembalian

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Pengembalian
import com.example.perpustakaan.data.repository.PengembalianRepository
import kotlinx.coroutines.launch

class DetailPengembalianViewModel(
    private val pengembalianRepository: PengembalianRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailPengembalianUiState())
        private set

    fun fetchDetailPengembalian(id_pengembalian: Int) {
        viewModelScope.launch {
            uiState = DetailPengembalianUiState(isLoading = true)
            try {
                val pengembalian = pengembalianRepository.getPengembalianById(id_pengembalian)
                uiState = DetailPengembalianUiState(
                    detailPengembalian = pengembalian.data.toUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailPengembalianUiState(
                    isError = true,
                    errorMessage = "Failed to fetch pengembalian details: ${e.message}"
                )
            }
        }
    }
}

data class DetailPengembalianUiState(
    val detailPengembalian: DetailPengembalianUiEvent = DetailPengembalianUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventNotEmpty: Boolean
        get() = detailPengembalian != DetailPengembalianUiEvent()
}

data class DetailPengembalianUiEvent(
    val id_pengembalian: Int = 0,
    val id_peminjaman: Int = 0,
    val nama: String = "",
    val tanggal_dikembalikan: String = ""
)

fun Pengembalian.toUiEvent(): DetailPengembalianUiEvent {
    return DetailPengembalianUiEvent(
        id_pengembalian = this.id_pengembalian,
        id_peminjaman = this.id_peminjaman,
        nama = nama,
        tanggal_dikembalikan = this.tanggal_dikembalikan
    )
}
