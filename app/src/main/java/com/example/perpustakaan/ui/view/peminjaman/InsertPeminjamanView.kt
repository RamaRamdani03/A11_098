package com.example.perpustakaan.ui.view.peminjaman

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.perpustakaan.data.ListPeminjaman
import com.example.perpustakaan.ui.customwidget.Dropdown
import com.example.perpustakaan.ui.customwidget.DynamicSelectTextField
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.peminjaman.InsertPeminjamanUiEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DestinasiInsertPeminjaman : AlamatNavigasi {
    override val route: String = "item_entrypeminjaman"
    override val titleRes = "Entry Peminjaman"
}

@Composable
fun FormPeminjamanInput(
    insertUiEvent: InsertPeminjamanUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPeminjamanUiEvent) -> Unit = {},
    enabled: Boolean = true
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
                    onValueChange(insertUiEvent.copy(tanggal_peminjaman = selectedDate))
                } else {
                    onValueChange(insertUiEvent.copy(tanggal_pengembalian = selectedDate))
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
            selectedValue = ListPeminjaman.Datanama().find { it.first == insertUiEvent.id_anggota }?.second ?: "",
            options = ListPeminjaman.Datanama().map { Dropdown(it.first, it.second) },
            label = "Nama Anggota",
            onValueChangedEvent = { selectednamaId ->
                onValueChange(insertUiEvent.copy(id_anggota = selectednamaId))
            }
        )

        DynamicSelectTextField(
            modifier = Modifier,
            selectedValue = ListPeminjaman.Datajudul().find { it.first == insertUiEvent.id_buku }?.second ?: "",
            options = ListPeminjaman.Datajudul().map { Dropdown(it.first, it.second) },
            label = "Judul",
            onValueChangedEvent = { selectedjudulId ->
                onValueChange(insertUiEvent.copy(id_buku = selectedjudulId))
            }
        )

        OutlinedTextField(
            value = insertUiEvent.tanggal_peminjaman,
            onValueChange = { },
            label = { Text("Tanggal Peminjaman") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker(isStartDate = true) },
            enabled = enabled,
            singleLine = true,
            readOnly = true
        )

        OutlinedTextField(
            value = insertUiEvent.tanggal_pengembalian,
            onValueChange = { },
            label = { Text("Tanggal Pengembalian") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker(isStartDate = false) },
            enabled = enabled,
            singleLine = true,
            readOnly = true
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