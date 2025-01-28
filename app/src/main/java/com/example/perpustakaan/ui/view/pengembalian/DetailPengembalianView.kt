package com.example.perpustakaan.ui.view.pengembalian

import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiDetailPengembalian : AlamatNavigasi {
    override val route = "detailpengembalian"
    override val titleRes = "Detail Pengembalian"
    const val id_pengembalian = "id_pengembalian"
    val routeWithArgs = "$route/{$id_pengembalian}"
}