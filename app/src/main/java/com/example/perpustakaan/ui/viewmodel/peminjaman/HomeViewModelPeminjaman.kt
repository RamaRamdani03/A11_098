package com.example.perpustakaan.ui.viewmodel.peminjaman

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Peminjaman
import com.example.perpustakaan.data.repository.PeminjamanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PeminjamanUiState {
    data class Success(val peminjaman: List<Peminjaman>) : PeminjamanUiState()
    object Error : PeminjamanUiState()
    object Loading : PeminjamanUiState()
}

class HomeViewModelPeminjaman(private val peminjamanRepository: PeminjamanRepository) : ViewModel() {

    private var _peminjamanUiState = mutableStateOf<PeminjamanUiState>(PeminjamanUiState.Loading)
    val peminjamanUiState: State<PeminjamanUiState> get() = _peminjamanUiState

    init {
        getPeminjaman()
    }

    fun getPeminjaman() {
        viewModelScope.launch {
            _peminjamanUiState.value = PeminjamanUiState.Loading

            try {
                val peminjamanList = peminjamanRepository.getPeminjaman()

                if (peminjamanList.isNotEmpty()) {
                    _peminjamanUiState.value = PeminjamanUiState.Success(peminjamanList)
                } else {
                    _peminjamanUiState.value = PeminjamanUiState.Error
                }
            } catch (e: IOException) {
                _peminjamanUiState.value = PeminjamanUiState.Error
            } catch (e: HttpException) {
                _peminjamanUiState.value = PeminjamanUiState.Error
            } catch (e: Exception) {
                _peminjamanUiState.value = PeminjamanUiState.Error
            }
        }
    }

    fun deletePeminjaman(idPeminjaman: Int) {
        viewModelScope.launch {
            try {
                peminjamanRepository.deletePeminjaman(idPeminjaman)
                getPeminjaman()
            } catch (e: IOException) {
                _peminjamanUiState.value = PeminjamanUiState.Error
            } catch (e: HttpException) {
                _peminjamanUiState.value = PeminjamanUiState.Error
            } catch (e: Exception) {
                _peminjamanUiState.value = PeminjamanUiState.Error
            }
        }
    }
}
