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
    }
}