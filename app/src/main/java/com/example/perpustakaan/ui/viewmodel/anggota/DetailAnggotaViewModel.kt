package com.example.perpustakaan.ui.viewmodel.anggota

import com.example.perpustakaan.data.model.Anggota

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