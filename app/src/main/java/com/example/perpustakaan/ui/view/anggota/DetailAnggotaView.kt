package com.example.perpustakaan.ui.view.anggota

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiDetailAnggota : AlamatNavigasi {
    override val route = "detailanggota"
    override val titleRes = "Detail Anggota"
    const val id_anggota = "id_anggota"
    val routeWithArgs = "$route/{$id_anggota}"
}