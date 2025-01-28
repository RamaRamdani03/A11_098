package com.example.perpustakaan.ui.viewmodel.pengembalian

import com.example.perpustakaan.data.model.Pengembalian

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