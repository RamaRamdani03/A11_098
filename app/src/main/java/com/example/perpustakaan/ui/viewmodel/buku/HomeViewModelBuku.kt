package com.example.perpustakaan.ui.viewmodel.buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Buku
import com.example.perpustakaan.data.repository.BukuRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class BukuUiState {
    data class Success(val buku: List<Buku>) : BukuUiState()
    object Error : BukuUiState()
    object Loading : BukuUiState()
}

class HomeViewModelBuku(private val bukuRepository: BukuRepository) : ViewModel() {

    var bukuUiState: BukuUiState by mutableStateOf(BukuUiState.Loading)
        private set

    var searchQuery: String by mutableStateOf("")
        private set

    init {
        getBuku()
    }

    fun getBuku() {
        viewModelScope.launch {
            bukuUiState = BukuUiState.Loading
            bukuUiState = try {
                val bukuList = bukuRepository.getBuku()
                BukuUiState.Success(bukuList)
            } catch (e: IOException) {
                BukuUiState.Error
            } catch (e: HttpException) {
                BukuUiState.Error
            }
        }
    }

    fun deleteBuku(idBuku: Int) {
        viewModelScope.launch {
            try {
                bukuRepository.deleteBuku(idBuku)
                getBuku()
            } catch (e: IOException) {
                bukuUiState = BukuUiState.Error
            } catch (e: HttpException) {
                bukuUiState = BukuUiState.Error
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun getFilteredBuku(): List<Buku> {
        return when (val state = bukuUiState) {
            is BukuUiState.Success -> {
                state.buku.filter { buku ->
                    buku.judul.contains(searchQuery, ignoreCase = true) ||
                            buku.penulis.contains(searchQuery, ignoreCase = true) ||
                            buku.kategori.contains(searchQuery, ignoreCase = true)
                }
            }
            else -> emptyList()
        }
    }
}