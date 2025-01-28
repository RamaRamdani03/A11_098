package com.example.perpustakaan.ui.view.peminjaman

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiUpdatePeminjaman : AlamatNavigasi {
    override val route: String = "updatepeminjaman"
    override val titleRes: String = "Update Peminjaman"
    const val id_peminjaman = "id_peminjaman"
    val routesWithArg = "$route/{$id_peminjaman}"
}