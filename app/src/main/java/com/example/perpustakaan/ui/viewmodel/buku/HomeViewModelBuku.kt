package com.example.perpustakaan.ui.viewmodel.buku

import com.example.perpustakaan.data.model.Buku

sealed class BukuUiState {
    data class Success(val buku: List<Buku>) : BukuUiState()
    object Error : BukuUiState()
    object Loading : BukuUiState()
}