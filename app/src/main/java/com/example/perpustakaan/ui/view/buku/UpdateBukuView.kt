package com.example.perpustakaan.ui.view.buku

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiUpdateBuku: AlamatNavigasi {
    override val route = "update"
    override val titleRes: String = "Update Buku"
    const val id_buku = "id_buku"
    val routesWithArg = "$route/{$id_buku}"
}