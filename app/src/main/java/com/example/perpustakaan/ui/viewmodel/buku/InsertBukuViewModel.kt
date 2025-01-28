package com.example.perpustakaan.ui.viewmodel.buku

import com.example.perpustakaan.data.model.Buku
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.repository.BukuRepository
import kotlinx.coroutines.launch

class InsertBukuViewModel(private val bukuRepository: BukuRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertBukuUiState())
        private set

    fun updateInsertBukuState(insertUiEvent: InsertBukuUiEvent) {
        uiState = InsertBukuUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun insertBuku() {
        val event = uiState.insertUiEvent

        if (event.judul.isBlank() || event.penulis.isBlank() || event.kategori.isBlank() || event.status.isBlank()) {
            println("Gagal menyimpan: Semua data harus diisi.")
            return
        }

        viewModelScope.launch {
            try {
                println("Menyimpan buku: $event")
                bukuRepository.insertBuku(event.toBuku())
                println("Buku berhasil disimpan.")
            } catch (e: Exception) {
                println("Error saat menyimpan buku: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}


data class InsertBukuUiState(
    val insertUiEvent: InsertBukuUiEvent = InsertBukuUiEvent(),
)

data class InsertBukuUiEvent(
    val id_buku: Int = 0,
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = ""
)

fun InsertBukuUiEvent.toBuku(): Buku = Buku(
    id_buku = 0,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

fun Buku.toUiStateBuku(): InsertBukuUiState = InsertBukuUiState(
    insertUiEvent = toInsertBukuUiEvent()
)

fun Buku.toInsertBukuUiEvent(): InsertBukuUiEvent = InsertBukuUiEvent(
    id_buku = 0,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)