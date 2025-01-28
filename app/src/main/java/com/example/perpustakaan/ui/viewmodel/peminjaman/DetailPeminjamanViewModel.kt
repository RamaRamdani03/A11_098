package com.example.perpustakaan.ui.viewmodel.peminjaman

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Peminjaman
import com.example.perpustakaan.data.repository.PeminjamanRepository
import kotlinx.coroutines.launch

class DetailPeminjamanViewModel(
    private val peminjamanRepository: PeminjamanRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailPeminjamanUiState())
        private set

    // Fetch detail peminjaman by ID
    fun fetchDetailPeminjaman(id_peminjaman: Int) {
        viewModelScope.launch {
            uiState = DetailPeminjamanUiState(isLoading = true)
            try {
                val peminjaman = peminjamanRepository.getPeminjamanById(id_peminjaman)
                uiState = DetailPeminjamanUiState(
                    detailPeminjaman = peminjaman.data.toUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailPeminjamanUiState(
                    isError = true,
                    errorMessage = "Failed to fetch peminjaman details: ${e.message}"
                )
            }
        }
    }
}

data class DetailPeminjamanUiState(
    val detailPeminjaman: DetailPeminjamanUiEvent = DetailPeminjamanUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailPeminjaman != DetailPeminjamanUiEvent()
}

data class DetailPeminjamanUiEvent(
    val id_peminjaman: Int = 0,
    val tanggal_peminjaman: String = "",
    val tanggal_pengembalian: String = "",
    val judul: String = "",
    val nama: String = "",
    val status: String = ""
)

fun Peminjaman.toUiEvent(): DetailPeminjamanUiEvent {
    return DetailPeminjamanUiEvent(
        id_peminjaman = id_peminjaman,
        tanggal_peminjaman = tanggal_peminjaman,
        tanggal_pengembalian = tanggal_pengembalian,
        judul = judul,
        nama = nama,
        status = status
    )
}
