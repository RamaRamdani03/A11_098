package com.example.perpustakaan.ui.view.pengembalian

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiUpdatePengembalian : AlamatNavigasi {
    override val route = "update_pengembalian"
    override val titleRes: String = "Update Pengembalian"
    const val id_pengembalian = "id_pengembalian"
    val routesWithArg = "$route/{$id_pengembalian}"
}