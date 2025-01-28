package com.example.perpustakaan.ui.viewmodel.buku

import com.example.perpustakaan.data.model.Buku

data class InsertBukuUiState(
    val insertUiEvent: InsertBukuUiEvent = InsertBukuUiEvent(),
)

data class InsertBukuUiEvent(
    val id_buku: Int = 0,
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = ""
)

fun InsertBukuUiEvent.toBuku(): Buku = Buku(
    id_buku = 0,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

fun Buku.toUiStateBuku(): InsertBukuUiState = InsertBukuUiState(
    insertUiEvent = toInsertBukuUiEvent()
)

fun Buku.toInsertBukuUiEvent(): InsertBukuUiEvent = InsertBukuUiEvent(
    id_buku = 0,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)