package com.example.perpustakaan.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.ui.viewmodel.PenyediaViewModel
import com.example.perpustakaan.ui.viewmodel.anggota.HomeUiState
import com.example.perpustakaan.ui.viewmodel.anggota.HomeViewModelAnggota
import com.example.perpustakaan.ui.viewmodel.buku.BukuUiState
import com.example.perpustakaan.ui.viewmodel.buku.HomeViewModelBuku
import com.example.perpustakaan.ui.viewmodel.peminjaman.HomeViewModelPeminjaman
import com.example.perpustakaan.ui.viewmodel.peminjaman.PeminjamanUiState

object ListPeminjaman {
    @Composable
    fun Datanama(
        namaHome: HomeViewModelAnggota = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<Int, String>> {
        val namaState = namaHome.anggotaUIState

        return when (namaState) {
            is HomeUiState.Success -> {
                namaState.anggota.map { it.id_anggota to it.nama }
            }
            else -> {
                emptyList()
            }
        }
    }

    @Composable
    fun Datajudul(
        lokasibuku: HomeViewModelBuku = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<Int, String>> {
        val judulState = lokasibuku.bukuUiState

        return when (judulState) {
            is BukuUiState.Success -> {
                judulState.buku.map { it.id_buku to it.judul }
            }
            else -> {
                emptyList()
            }
        }
    }

    @Composable
    fun idPeminjaman(
        lokasipeminjaman: HomeViewModelPeminjaman = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Int> {
        val peminjamanState = lokasipeminjaman.peminjamanUiState.value

        return when (peminjamanState) {
            is PeminjamanUiState.Success -> {
                peminjamanState.peminjaman.map { it.id_peminjaman }
            }
            else -> {
                emptyList()
            }
        }
    }

    @Composable
    fun tanggalKembali(
        tglKembaliViewModel : HomeViewModelPeminjaman = viewModel(factory = PenyediaViewModel.Factory)
    ) : List<Pair<String, Int>>
    {
        val dataTglKembali = tglKembaliViewModel.peminjamanUiState.value

        return when (dataTglKembali) {
            is PeminjamanUiState.Success -> {
                dataTglKembali.peminjaman.map { it.tanggal_pengembalian to it.id_peminjaman }
            }
            else -> {
                emptyList()
            }
        }
    }
}
