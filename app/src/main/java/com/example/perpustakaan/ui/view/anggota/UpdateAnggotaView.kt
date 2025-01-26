package com.example.perpustakaan.ui.view.anggota

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
import com.example.perpustakaan.ui.viewmodel.anggota.UpdateAnggotaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateAnggota: AlamatNavigasi {
    override val route = "updateanggota"
    override val titleRes: String = "Update Anggota"
    const val id_anggota = "id_anggota"
    val routesWithArg = "$route/{$id_anggota}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAnggotaScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: UpdateAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateAnggota.titleRes,
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
            onAnggotaValueChange = viewModel::updateInsertAnggotaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAnggota()
                    onNavigate()
                }
            }
        )
    }
}