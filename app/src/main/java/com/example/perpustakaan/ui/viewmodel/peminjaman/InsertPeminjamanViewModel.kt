package com.example.perpustakaan.ui.viewmodel.peminjaman

import com.example.perpustakaan.data.model.Peminjaman

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