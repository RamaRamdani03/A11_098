package com.example.perpustakaan.ui.viewmodel.buku

import com.example.perpustakaan.data.model.Buku

data class DetailBukuUiState(
    val detailUiEvent: InsertBukuUiEvent = InsertBukuUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertBukuUiEvent()
}

fun Buku.toDetailUiEvent(): InsertBukuUiEvent {
    return InsertBukuUiEvent(
        id_buku = id_buku,
        judul = judul,
        penulis = penulis,
        kategori = kategori,
        status = status
    )
}