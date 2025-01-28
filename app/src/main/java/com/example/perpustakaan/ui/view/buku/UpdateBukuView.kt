package com.example.perpustakaan.ui.view.buku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.ui.customwidget.CostumeTopAppBar
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.PenyediaViewModel
import com.example.perpustakaan.ui.viewmodel.buku.InsertBukuUiEvent
import com.example.perpustakaan.ui.viewmodel.buku.InsertBukuUiState
import com.example.perpustakaan.ui.viewmodel.buku.UpdateBukuViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateBuku: AlamatNavigasi {
    override val route = "update"
    override val titleRes: String = "Update Buku"
    const val id_buku = "id_buku"
    val routesWithArg = "$route/{$id_buku}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBukuScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: UpdateBukuViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                showRefresh = true,
                navigateUp = onBack
            )
        }
    ) { paddingValues ->
        EntryBody(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            insertUiState = viewModel.updateUiState,
            onAnggotaValueChange = viewModel::updateInsertBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateBuku()
                    onNavigate() // Navigate after save
                }
            }
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertBukuUiState,
    onAnggotaValueChange: (InsertBukuUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormBukuInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAnggotaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}