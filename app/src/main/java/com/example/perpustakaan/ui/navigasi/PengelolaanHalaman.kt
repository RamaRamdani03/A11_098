package com.example.perpustakaan.ui.navigasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perpustakaan.ui.view.DestinasiHome
import com.example.perpustakaan.ui.view.HomeNavbar
import com.example.perpustakaan.ui.view.anggota.DestinasiDetailAnggota
import com.example.perpustakaan.ui.view.anggota.DestinasiHomeAnggota
import com.example.perpustakaan.ui.view.anggota.DestinasiInsertAnggota
import com.example.perpustakaan.ui.view.anggota.DestinasiUpdateAnggota
import com.example.perpustakaan.ui.view.anggota.DetailAnggotaView
import com.example.perpustakaan.ui.view.anggota.EntryAnggotaScreen
import com.example.perpustakaan.ui.view.anggota.HomeScreenAnggota
import com.example.perpustakaan.ui.view.anggota.UpdateAnggotaScreen
import com.example.perpustakaan.ui.view.buku.DestinasiDetailBuku
import com.example.perpustakaan.ui.view.buku.DestinasiHomeBuku
import com.example.perpustakaan.ui.view.buku.DestinasiInsertBuku
import com.example.perpustakaan.ui.view.buku.DestinasiUpdateBuku
import com.example.perpustakaan.ui.view.buku.DetailBukuView
import com.example.perpustakaan.ui.view.buku.EntryBukuScreen
import com.example.perpustakaan.ui.view.buku.HomeScreenBuku
import com.example.perpustakaan.ui.view.buku.UpdateBukuScreen
import com.example.perpustakaan.ui.view.peminjaman.DestinasiDetailPeminjaman
import com.example.perpustakaan.ui.view.peminjaman.DestinasiHomePeminjaman
import com.example.perpustakaan.ui.view.peminjaman.DestinasiInsertPeminjaman
import com.example.perpustakaan.ui.view.peminjaman.DestinasiUpdatePeminjaman
import com.example.perpustakaan.ui.view.peminjaman.DetailPeminjamanView
import com.example.perpustakaan.ui.view.peminjaman.EntryPeminjamanScreen
import com.example.perpustakaan.ui.view.peminjaman.HomeScreenPeminjaman
import com.example.perpustakaan.ui.view.peminjaman.UpdatePeminjamanScreen
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

        //Anggota
        composable(
            route = DestinasiHomeAnggota.route
        ) {
            HomeScreenAnggota(
                onDetailAnggotaClick = { id_anggota ->
                    navController.navigate("${DestinasiDetailAnggota.route}/$id_anggota")
                    println("PengelolaanHalaman: id_ = $id_anggota")
                },
                navigateToItemAnggotaEntry = { navController.navigate(DestinasiInsertAnggota.route) },
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToEditAnggota = { id_anggota ->
                    navController.navigate("${DestinasiUpdateAnggota.route}/$id_anggota")
                },
                modifier = Modifier
            )
        }

        composable(
            route = DestinasiInsertAnggota.route
        ) {
            EntryAnggotaScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetailAnggota.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailAnggota.id_anggota){
                    type = NavType.IntType
                }
            )
        ){
                backStackEntry ->
            val id_anggota = backStackEntry.arguments?.getInt(DestinasiDetailAnggota.id_anggota)
            id_anggota?.let {
                DetailAnggotaView(
                    idAnggota = it,
                    onBackClick = {navController.popBackStack()}
                )
            }
        }

        composable(
            DestinasiUpdateAnggota.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateAnggota.id_anggota) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_anggota = it.arguments?.getInt(DestinasiUpdateAnggota.id_anggota)
            id_anggota?.let { id_anggota ->
                UpdateAnggotaScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                )
            }
        }

        //Buku
        composable(
            route = DestinasiHomeBuku.route
        ) {
            HomeScreenBuku(
                onDetailBukuClick = { id_buku ->
                    navController.navigate("${DestinasiDetailBuku.route}/$id_buku")
                    println("PengelolaanHalaman: id_ = $id_buku")
                },
                navigateToItemBukuEntry= { navController.navigate(DestinasiInsertBuku.route) },
                navigateBack = { navController.popBackStack() },
                navigateToEditBuku = {id_buku ->
                    navController.navigate("${DestinasiUpdateBuku.route}/$id_buku")
                },
                modifier = Modifier
            )
        }

        composable(
            route = DestinasiInsertBuku.route
        ) {
            EntryBukuScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetailBuku.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailBuku.id_buku){
                    type = NavType.IntType
                }
            )
        ){
                backStackEntry ->
            val id_buku = backStackEntry.arguments?.getInt(DestinasiDetailBuku.id_buku)
            id_buku?.let {
                DetailBukuView(
                    idBuku = it,
                    onBackClick = {navController.popBackStack()}
                )
            }
        }

        composable(
            DestinasiUpdateBuku.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBuku.id_buku) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_buku = it.arguments?.getInt(DestinasiUpdateBuku.id_buku)
            id_buku?.let { id_buku ->
                UpdateBukuScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                )
            }
        }

        //Peminjaman
        composable(
            route = DestinasiHomePeminjaman.route
        ) {
            HomeScreenPeminjaman(
                onDetailPeminjamanClick = { id_peminjaman ->
                    navController.navigate("${DestinasiDetailPeminjaman.route}/$id_peminjaman")
                    println("PengelolaanHalaman: id_= $id_peminjaman")
                },
                navigateToItemPeminjamanEntry = { navController.navigate(DestinasiInsertPeminjaman.route) },
                navigateBack = { navController.popBackStack() },
                navigateToEditPeminjaman = { id_peminjaman ->
                    navController.navigate("${DestinasiUpdatePeminjaman.route}/$id_peminjaman")
                },
                modifier = Modifier
            )
        }

        composable(
            route = DestinasiInsertPeminjaman.route
        ) {
            EntryPeminjamanScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetailPeminjaman.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPeminjaman.id_peminjaman){
                    type = NavType.IntType
                }
            )
        ){
                backStackEntry ->
            val id_peminjaman = backStackEntry.arguments?.getInt(DestinasiDetailPeminjaman.id_peminjaman)
            id_peminjaman?.let {
                DetailPeminjamanView(
                    idPeminjaman = it,
                    onBackClick = {navController.popBackStack()}
                )
            }
        }

        composable(
            DestinasiUpdatePeminjaman.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePeminjaman.id_peminjaman) {
                    type = NavType.IntType
                }
            )
        ) {
            val id_peminjaman = it.arguments?.getInt(DestinasiUpdatePeminjaman.id_peminjaman)
            id_peminjaman?.let { id_peminjaman ->
                UpdatePeminjamanScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.popBackStack()
                    },
                    modifier = Modifier,
                )
            }
        }
    }
}