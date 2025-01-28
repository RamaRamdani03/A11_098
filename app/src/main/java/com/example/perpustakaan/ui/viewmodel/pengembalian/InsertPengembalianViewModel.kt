package com.example.perpustakaan.ui.viewmodel.pengembalian

import com.example.perpustakaan.data.model.Pengembalian

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
