package com.example.perpustakaan.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perpustakaan.PerpustakaanAplications
import com.example.perpustakaan.ui.viewmodel.anggota.DetailAnggotaViewModel
import com.example.perpustakaan.ui.viewmodel.anggota.HomeViewModelAnggota
import com.example.perpustakaan.ui.viewmodel.anggota.InsertAnggotaViewModel
import com.example.perpustakaan.ui.viewmodel.anggota.UpdateAnggotaViewModel
import com.example.perpustakaan.ui.viewmodel.buku.DetailBukuViewModel
import com.example.perpustakaan.ui.viewmodel.buku.HomeViewModelBuku
import com.example.perpustakaan.ui.viewmodel.buku.InsertBukuViewModel
import com.example.perpustakaan.ui.viewmodel.buku.UpdateBukuViewModel
import com.example.perpustakaan.ui.viewmodel.peminjaman.DetailPeminjamanViewModel
import com.example.perpustakaan.ui.viewmodel.peminjaman.HomeViewModelPeminjaman
import com.example.perpustakaan.ui.viewmodel.peminjaman.InsertPeminjamanViewModel
import com.example.perpustakaan.ui.viewmodel.peminjaman.UpdatePeminjamanViewModel
import com.example.perpustakaan.ui.viewmodel.pengembalian.DetailPengembalianViewModel
import com.example.perpustakaan.ui.viewmodel.pengembalian.HomeViewModelPengembalian
import com.example.perpustakaan.ui.viewmodel.pengembalian.InsertPengembalianViewModel
import com.example.perpustakaan.ui.viewmodel.pengembalian.UpdatePengembalianViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        //Anggota
        initializer { HomeViewModelAnggota(perpustakaanAPP().container.anggotaRepository) }
        initializer { InsertAnggotaViewModel(perpustakaanAPP().container.anggotaRepository) }
        initializer { UpdateAnggotaViewModel(createSavedStateHandle(),perpustakaanAPP().container.anggotaRepository) }
        initializer { DetailAnggotaViewModel(perpustakaanAPP().container.anggotaRepository) }

        //Buku
        initializer { HomeViewModelBuku(perpustakaanAPP().container.bukuRepository) }
        initializer { InsertBukuViewModel(perpustakaanAPP().container.bukuRepository) }
        initializer { UpdateBukuViewModel(createSavedStateHandle(),perpustakaanAPP().container.bukuRepository) }
        initializer { DetailBukuViewModel(perpustakaanAPP().container.bukuRepository) }

        //Penminjaman
        initializer { HomeViewModelPeminjaman(perpustakaanAPP().container.peminjamanRepository) }
        initializer { InsertPeminjamanViewModel(perpustakaanAPP().container.peminjamanRepository) }
        initializer { UpdatePeminjamanViewModel(createSavedStateHandle(),perpustakaanAPP().container.peminjamanRepository) }
        initializer { DetailPeminjamanViewModel(perpustakaanAPP().container.peminjamanRepository) }

        //Pengembalian
        initializer { HomeViewModelPengembalian(perpustakaanAPP().container.pengembalianRepository) }
        initializer { InsertPengembalianViewModel(perpustakaanAPP().container.pengembalianRepository) }
        initializer { UpdatePengembalianViewModel(createSavedStateHandle(),perpustakaanAPP().container.pengembalianRepository) }
        initializer { DetailPengembalianViewModel(perpustakaanAPP().container.pengembalianRepository) }
    }
}

fun CreationExtras.perpustakaanAPP(): PerpustakaanAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpustakaanAplications)