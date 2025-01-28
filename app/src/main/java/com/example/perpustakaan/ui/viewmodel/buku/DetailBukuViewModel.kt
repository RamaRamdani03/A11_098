package com.example.perpustakaan.ui.viewmodel.buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Buku
import com.example.perpustakaan.data.repository.BukuRepository
import kotlinx.coroutines.launch

class DetailBukuViewModel(private val bukuRepository: BukuRepository): ViewModel() {

    var uiState by mutableStateOf(DetailBukuUiState())
        private set

    fun fetchDetailBuku(id_buku: Int) {
        viewModelScope.launch {
            uiState = DetailBukuUiState(isLoading = true)
            try {
                val buku = bukuRepository.getBukuById(id_buku)
                uiState = DetailBukuUiState(detailUiEvent = buku.data.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailBukuUiState(
                    isError = true,
                    errorMessage = "Failed to fetch buku details: ${e.message}"
                )
            }
        }
    }
}

data class DetailBukuUiState(
    val detailUiEvent: InsertBukuUiEvent = InsertBukuUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertBukuUiEvent()
}

fun Buku.toDetailUiEvent(): InsertBukuUiEvent {
    return InsertBukuUiEvent(
        id_buku = id_buku,
        judul = judul,
        penulis = penulis,
        kategori = kategori,
        status = status
    )
}