package com.example.perpustakaan.ui.navigasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.perpustakaan.ui.view.DestinasiHome
import com.example.perpustakaan.ui.view.HomeNavbar
import com.example.perpustakaan.ui.view.anggota.DestinasiHomeAnggota
import com.example.perpustakaan.ui.view.buku.DestinasiHomeBuku
import com.example.perpustakaan.ui.view.peminjaman.DestinasiHomePeminjaman
import com.example.perpustakaan.ui.view.pengembalian.DestinasiHomePengembalian

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PengelolaanHalaman (
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeNavbar(
                onBuku = { navController.navigate(DestinasiHomeBuku.route) },
                onAnggota = { navController.navigate(DestinasiHomeAnggota.route) },
                onPeminjaman = { navController.navigate(DestinasiHomePeminjaman.route) },
                onPengembalian = { navController.navigate(DestinasiHomePengembalian.route) }
            )
        }
    }
}