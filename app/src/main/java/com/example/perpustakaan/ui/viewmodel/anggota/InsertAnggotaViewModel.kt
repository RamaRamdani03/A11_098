package com.example.perpustakaan.ui.viewmodel.anggota

import androidx.lifecycle.ViewModel
import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.data.repository.AnggotaRepository

class InsertAnggotaViewModel(private val anggotaRepository: AnggotaRepository) : ViewModel() {

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
