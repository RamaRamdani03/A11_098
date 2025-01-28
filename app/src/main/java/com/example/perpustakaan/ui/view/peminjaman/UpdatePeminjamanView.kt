package com.example.perpustakaan.ui.view.peminjaman

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.ui.customwidget.CostumeTopAppBar
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.PenyediaViewModel
import com.example.perpustakaan.ui.viewmodel.peminjaman.UpdatePeminjamanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePeminjaman : AlamatNavigasi {
    override val route: String = "updatepeminjaman"
    override val titleRes: String = "Update Peminjaman"
    const val id_peminjaman = "id_peminjaman"
    val routesWithArg = "$route/{$id_peminjaman}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePeminjamanScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: UpdatePeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePeminjaman.titleRes,
                canNavigateBack = true,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack
            )
        }
    ) { paddingValues ->
        EntryBody(
            modifier = Modifier.padding(paddingValues),
            insertUiState = viewModel.updateUiState,
            onPeminjamanValueChange = viewModel::updateInsertPeminjamanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePeminjaman()
                    onNavigate()
                }
            }
        )
    }
}