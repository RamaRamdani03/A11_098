package com.example.perpustakaan.ui.viewmodel.peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Peminjaman
import com.example.perpustakaan.data.repository.PeminjamanRepository
import kotlinx.coroutines.launch

class InsertPeminjamanViewModel(
    private val peminjamanRepository: PeminjamanRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPeminjamanUiState())
        private set

    fun updateInsertPeminjamanState(insertUiEvent: InsertPeminjamanUiEvent) {
        uiState = InsertPeminjamanUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun insertPeminjaman() {
        val event = uiState.insertUiEvent

        // Validasi input
        if (event.id_buku <= 0 || event.id_anggota <= 0 || event.tanggal_peminjaman.isBlank() || event.tanggal_pengembalian.isBlank()) {
            println("Gagal menyimpan: Semua data harus diisi.")
            return
        }

        viewModelScope.launch {
            try {
                println("Menyimpan peminjaman: $event")
                peminjamanRepository.insertPeminjaman(event.toPeminjaman())
                println("Peminjaman berhasil disimpan.")
            } catch (e: Exception) {
                println("Error saat menyimpan peminjaman: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}

data class InsertPeminjamanUiState(
    val insertUiEvent: InsertPeminjamanUiEvent = InsertPeminjamanUiEvent(),
)

data class InsertPeminjamanUiEvent(
    val id_peminjaman: Int = 0,
    val id_buku: Int = 0,
    val id_anggota: Int = 0,
    val tanggal_peminjaman: String = "",
    val tanggal_pengembalian: String = "",
    val judul: String = "",
    val nama: String = "",
    val status: String = ""
)

fun InsertPeminjamanUiEvent.toPeminjaman(): Peminjaman = Peminjaman(
    id_peminjaman = 0,  // id_peminjaman akan di auto-generate di database
    id_buku = id_buku,
    id_anggota = id_anggota,
    tanggal_peminjaman = tanggal_peminjaman,
    tanggal_pengembalian = tanggal_pengembalian,
    judul = judul,
    nama = nama,
    status = status

)

fun Peminjaman.toUiStatePeminjaman(): InsertPeminjamanUiState = InsertPeminjamanUiState(
    insertUiEvent = toInsertPeminjamanUiEvent()
)

fun Peminjaman.toInsertPeminjamanUiEvent(): InsertPeminjamanUiEvent = InsertPeminjamanUiEvent(
    id_peminjaman = 0,
    id_buku = id_buku,
    id_anggota = id_anggota,
    tanggal_peminjaman = tanggal_peminjaman,
    tanggal_pengembalian = tanggal_pengembalian,
    judul = judul,
    nama = nama,
    status = status
)
