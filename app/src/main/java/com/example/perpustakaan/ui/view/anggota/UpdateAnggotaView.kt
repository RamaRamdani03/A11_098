package com.example.perpustakaan.ui.view.anggota

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi


object DestinasiUpdateAnggota: AlamatNavigasi {
    override val route = "updateanggota"
    override val titleRes: String = "Update Anggota"
    const val id_anggota = "id_anggota"
    val routesWithArg = "$route/{$id_anggota}"
}