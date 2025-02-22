package com.example.perpustakaan.ui.view.pengembalian

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
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
import com.example.perpustakaan.ui.viewmodel.pengembalian.UpdatePengembalianViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DestinasiUpdatePengembalian : AlamatNavigasi {
    override val route = "update_pengembalian"
    override val titleRes: String = "Update Pengembalian"
    const val id_pengembalian = "id_pengembalian"
    val routesWithArg = "$route/{$id_pengembalian}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePengembalianScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: UpdatePengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePengembalian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                showRefresh = false,
                navigateUp = onBack
            )
        }
    ) { paddingValues ->
        EntryBody(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            insertUiState = viewModel.updateUiState,
            onPengembalianValueChange = viewModel::updateInsertPengembalianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePengembalian()
                    onNavigate()
                }
            }
        )
    }
}

@Composable
fun EntryBody(
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
            shape = androidx.compose.material3.MaterialTheme.shapes.small,
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
    onValueChange: (InsertPengembalianUiEvent) -> Unit = {}
) {
    val context = LocalContext.current
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val calendar = remember { Calendar.getInstance() }

    fun showDatePicker(isStartDate: Boolean) {
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val selectedDate = dateFormat.format(calendar.time)
                if (isStartDate) {
                    onValueChange(insertUiEvent.copy(tanggal_dikembalikan = selectedDate))
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

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
            onValueChange = { },
            label = { Text("Tanggal Dikembalikan (YYYY-MM-DD)") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker(isStartDate = true) },
            readOnly = true,
            singleLine = true
        )

        Text(
            text = "Isi Semua Data!",
            modifier = Modifier.padding(12.dp)
        )

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}