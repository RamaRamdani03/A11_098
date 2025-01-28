package com.example.perpustakaan.ui.view.peminjaman

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiDetailPeminjaman : AlamatNavigasi {
    override val route = "detailpeminjaman"
    override val titleRes = "Detail Peminjaman"
    const val id_peminjaman = "id_peminjaman"
    val routeWithArgs = "$route/{$id_peminjaman}"
}