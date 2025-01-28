package com.example.perpustakaan.ui.view.pengembalian

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.data.ListPeminjaman
import com.example.perpustakaan.ui.customwidget.CostumeTopAppBar
import com.example.perpustakaan.ui.customwidget.Dropdown
import com.example.perpustakaan.ui.customwidget.DynamicSelectTextField
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.PenyediaViewModel
import com.example.perpustakaan.ui.viewmodel.pengembalian.InsertPengembalianUiEvent
import com.example.perpustakaan.ui.viewmodel.pengembalian.InsertPengembalianUiState
import com.example.perpustakaan.ui.viewmodel.pengembalian.InsertPengembalianViewModel
import kotlinx.coroutines.launch

object DestinasiInsertPengembalian : AlamatNavigasi {
    override val route: String = "pengembalian_entry"
    override val titleRes = "Entry Pengembalian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPengembalianScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPengembalian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                showRefresh = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPengembalian(
            insertUiState = viewModel.uiState,
            onPengembalianValueChange = viewModel::updateInsertPengembalianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPengembalian()
                    navigateBack() // Navigate back after insertion
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPengembalian(
    insertUiState: InsertPengembalianUiState,
    onPengembalianValueChange: (InsertPengembalianUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormPengembalianInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPengembalianValueChange,
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

@Composable
fun FormPengembalianInput(
    insertUiEvent: InsertPengembalianUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPengembalianUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DynamicSelectTextField(
            modifier = Modifier,
            selectedValue = ListPeminjaman.idPeminjaman().find { it == insertUiEvent.id_peminjaman }?.toString() ?: "",
            options = ListPeminjaman.idPeminjaman().map { Dropdown(it, it.toString()) },
            label = "ID Peminjaman",
            onValueChangedEvent = { selectednamaId ->
                onValueChange(insertUiEvent.copy(id_peminjaman = selectednamaId))
            }
        )
        OutlinedTextField(
            value = insertUiEvent.tanggal_dikembalikan,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_dikembalikan = it)) },
            label = { Text("Tanggal Dikembalikan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
