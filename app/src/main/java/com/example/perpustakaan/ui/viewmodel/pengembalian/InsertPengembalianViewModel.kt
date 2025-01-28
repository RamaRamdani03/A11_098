package com.example.perpustakaan.ui.viewmodel.pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Pengembalian
import com.example.perpustakaan.data.repository.PengembalianRepository
import kotlinx.coroutines.launch

class InsertPengembalianViewModel(
    private val pengembalianRepository: PengembalianRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPengembalianUiState())
        private set

    fun updateInsertPengembalianState(insertUiEvent: InsertPengembalianUiEvent) {
        uiState = InsertPengembalianUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun insertPengembalian() {
        val event = uiState.insertUiEvent

        // Validate input
        if (event.id_peminjaman <= 0 || event.tanggal_dikembalikan.isBlank()) {
            println("Gagal menyimpan: Semua data harus diisi.")
            return
        }

        viewModelScope.launch {
            try {
                println("Menyimpan pengembalian: $event")
                pengembalianRepository.insertPengembalian(event.toPengembalian())
                println("Pengembalian berhasil disimpan.")
            } catch (e: Exception) {
                println("Error saat menyimpan pengembalian: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}

data class InsertPengembalianUiState(
    val insertUiEvent: InsertPengembalianUiEvent = InsertPengembalianUiEvent(),
)

data class InsertPengembalianUiEvent(
    val id_pengembalian: Int = 0,
    val id_peminjaman: Int = 0,
    val tanggal_dikembalikan: String = ""
)

fun InsertPengembalianUiEvent.toPengembalian(): Pengembalian = Pengembalian(
    id_pengembalian = 0,
    id_peminjaman = id_peminjaman,
    tanggal_dikembalikan = tanggal_dikembalikan
)

fun Pengembalian.toUiStatePengembalian(): InsertPengembalianUiState = InsertPengembalianUiState(
    insertUiEvent = toInsertPengembalianUiEvent()
)

fun Pengembalian.toInsertPengembalianUiEvent(): InsertPengembalianUiEvent = InsertPengembalianUiEvent(
    id_pengembalian = 0,
    id_peminjaman = id_peminjaman,
    tanggal_dikembalikan = tanggal_dikembalikan
)
