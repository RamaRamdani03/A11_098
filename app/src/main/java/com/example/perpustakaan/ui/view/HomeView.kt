package com.example.perpustakaan.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiHome: AlamatNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

@Composable
fun HomeNavbar(
    modifier: Modifier = Modifier,
    onAnggota: () -> Unit = {},
    onBuku: () -> Unit = {},
    onPeminjaman: () -> Unit = {},
    onPengembalian: () -> Unit = {},
) {

}