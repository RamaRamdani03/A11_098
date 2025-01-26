package com.example.perpustakaan.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.data.repository.AnggotaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState {
    data class Success(val anggota: List<Anggota>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModelAnggota(private val anggota: AnggotaRepository) : ViewModel() {

    var anggotaUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAnggota()
    }

    // Fetch all Anggota records
    fun getAnggota() {
        viewModelScope.launch {
            anggotaUIState = HomeUiState.Loading
            anggotaUIState = try {
                val anggotaList = anggota.getAnggota()
                HomeUiState.Success(anggotaList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    // Delete a specific Anggota record
    fun deleteAnggota(idAnggota: Int) {
        viewModelScope.launch {
            try {
                anggota.deleteAnggota(idAnggota)
                getAnggota()
            } catch (e: IOException) {
                anggotaUIState = HomeUiState.Error
            } catch (e: HttpException) {
                anggotaUIState = HomeUiState.Error
            }
        }
    }
}