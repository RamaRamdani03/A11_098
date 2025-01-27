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

object PenyediaViewModel {

    val Factory = viewModelFactory {
        //Anggota
        initializer { HomeViewModelAnggota(perpustakaanAPP().container.anggotaRepository) }
        initializer { InsertAnggotaViewModel(perpustakaanAPP().container.anggotaRepository) }
        initializer { UpdateAnggotaViewModel(createSavedStateHandle(),perpustakaanAPP().container.anggotaRepository) }
        initializer { DetailAnggotaViewModel(perpustakaanAPP().container.anggotaRepository) }
    }
}

fun CreationExtras.perpustakaanAPP(): PerpustakaanAplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpustakaanAplications)