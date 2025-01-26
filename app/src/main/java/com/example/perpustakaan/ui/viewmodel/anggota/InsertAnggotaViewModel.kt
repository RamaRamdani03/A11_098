package com.example.perpustakaan.ui.viewmodel.anggota

data class InsertAnggotaUiState(
    val insertUiEvent: InsertAnggotaUiEvent = InsertAnggotaUiEvent(),
)

data class InsertAnggotaUiEvent(
    val id_anggota: Int = 0,
    val nama: String = "",
    val email: String = "",
    val nomor_telepon: String = ""
)