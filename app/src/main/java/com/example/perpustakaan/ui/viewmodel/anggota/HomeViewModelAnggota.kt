package com.example.perpustakaan.ui.viewmodel.anggota

import com.example.perpustakaan.data.model.Anggota

sealed class HomeUiState {
    data class Success(val anggota: List<Anggota>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}