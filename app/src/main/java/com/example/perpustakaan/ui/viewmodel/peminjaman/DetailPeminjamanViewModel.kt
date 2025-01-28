package com.example.perpustakaan.ui.viewmodel.peminjaman

import com.example.perpustakaan.data.model.Peminjaman

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