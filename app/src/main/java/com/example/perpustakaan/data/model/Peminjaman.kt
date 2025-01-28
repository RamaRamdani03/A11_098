package com.example.perpustakaan.data.model

import com.example.perpustakaan.ui.viewmodel.peminjaman.InsertPeminjamanUiEvent
import com.example.perpustakaan.ui.viewmodel.peminjaman.InsertPeminjamanUiState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Peminjaman(
    val id_peminjaman: Int,
    val id_buku: Int,
    val id_anggota: Int,

    @SerialName("tanggal_peminjaman")
    val tanggal_peminjaman: String,

    @SerialName("tanggal_pengembalian")
    val tanggal_pengembalian: String,

    val judul: String,
    val nama: String,
    val status: String
)

@Serializable
data class PeminjamanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peminjaman>
)

@Serializable
data class PeminjamanResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Peminjaman
)

fun Peminjaman.toUiStatePeminjaman(): InsertPeminjamanUiState {
    return InsertPeminjamanUiState(
        insertUiEvent = InsertPeminjamanUiEvent(
            nama = nama,
            judul = judul,
            status = status,
            tanggal_peminjaman = tanggal_peminjaman,
            tanggal_pengembalian = tanggal_pengembalian
        )
    )
}

