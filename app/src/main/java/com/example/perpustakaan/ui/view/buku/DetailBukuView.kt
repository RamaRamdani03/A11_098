package com.example.perpustakaan.ui.view.buku

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiDetailBuku : AlamatNavigasi {
    override val route = "detailbuku"
    override val titleRes = "Detail Buku"
    const val id_buku = "id_buku"
    val routeWithArgs = "$route/{$id_buku}"
}