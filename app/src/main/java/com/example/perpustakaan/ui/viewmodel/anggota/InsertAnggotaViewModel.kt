package com.example.perpustakaan.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.data.repository.AnggotaRepository
import kotlinx.coroutines.launch

class InsertAnggotaViewModel(private val anggotaRepository: AnggotaRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertAnggotaUiState())
        private set

    fun updateInsertAnggotaState(insertUiEvent: InsertAnggotaUiEvent) {
        uiState = InsertAnggotaUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun insertAnggota() {
        val event = uiState.insertUiEvent

        // Validasi input sebelum insert
        if (event.nama.isBlank() || event.email.isBlank() || event.nomor_telepon.isBlank()) {
            println("Gagal menyimpan: Semua data harus diisi.")
            return
        }

        viewModelScope.launch {
            try {
                println("Menyimpan anggota: $event")
                anggotaRepository.insertAnggota(event.toAnggota())
                println("Anggota berhasil disimpan.")
            } catch (e: Exception) {
                println("Error saat menyimpan anggota: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}

data class InsertAnggotaUiState(
    val insertUiEvent: InsertAnggotaUiEvent = InsertAnggotaUiEvent(),
)

data class InsertAnggotaUiEvent(
    val id_anggota: Int = 0,
    val nama: String = "",
    val email: String = "",
    val nomor_telepon: String = ""
)

fun InsertAnggotaUiEvent.toAnggota(): Anggota = Anggota(
    id_anggota = 0,
    nama = nama,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Anggota.toUiStateAnggota(): InsertAnggotaUiState = InsertAnggotaUiState(
    insertUiEvent = toInsertAnggotaUiEvent()
)

fun Anggota.toInsertAnggotaUiEvent(): InsertAnggotaUiEvent = InsertAnggotaUiEvent(
    id_anggota = 0,
    nama = nama,
    email = email,
    nomor_telepon = nomor_telepon
)
