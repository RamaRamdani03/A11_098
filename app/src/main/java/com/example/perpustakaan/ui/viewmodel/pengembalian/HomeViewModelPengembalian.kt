package com.example.perpustakaan.ui.viewmodel.pengembalian

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.data.model.Pengembalian
import com.example.perpustakaan.data.repository.PengembalianRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PengembalianUiState {
    data class Success(val pengembalian: List<Pengembalian>) : PengembalianUiState()
    object Error : PengembalianUiState()
    object Loading : PengembalianUiState()
}

class HomeViewModelPengembalian(private val pengembalianRepository: PengembalianRepository) : ViewModel() {

    private var _pengembalianUiState = mutableStateOf<PengembalianUiState>(PengembalianUiState.Loading)
    val pengembalianUiState: State<PengembalianUiState> get() = _pengembalianUiState

    init {
        getPengembalian()
    }

    fun getPengembalian() {
        viewModelScope.launch {
            _pengembalianUiState.value = PengembalianUiState.Loading

            try {
                val pengembalianList = pengembalianRepository.getPengembalian()

                if (pengembalianList.isNotEmpty()) {
                    _pengembalianUiState.value = PengembalianUiState.Success(pengembalianList)
                } else {
                    _pengembalianUiState.value = PengembalianUiState.Error
                }
            } catch (e: IOException) {
                _pengembalianUiState.value = PengembalianUiState.Error
            } catch (e: HttpException) {
                _pengembalianUiState.value = PengembalianUiState.Error
            } catch (e: Exception) {
                _pengembalianUiState.value = PengembalianUiState.Error
            }
        }
    }

    fun deletePengembalian(idPengembalian: Int) {
        viewModelScope.launch {
            try {
                pengembalianRepository.deletePengembalian(idPengembalian)
                getPengembalian()
            } catch (e: IOException) {
                _pengembalianUiState.value = PengembalianUiState.Error
            } catch (e: HttpException) {
                _pengembalianUiState.value = PengembalianUiState.Error
            } catch (e: Exception) {
                _pengembalianUiState.value = PengembalianUiState.Error
            }
        }
    }
}
