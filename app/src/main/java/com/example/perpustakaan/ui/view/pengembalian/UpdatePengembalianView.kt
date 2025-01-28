package com.example.perpustakaan.ui.view.pengembalian

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
import com.example.perpustakaan.ui.viewmodel.pengembalian.InsertPengembalianUiEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DestinasiUpdatePengembalian : AlamatNavigasi {
    override val route = "update_pengembalian"
    override val titleRes: String = "Update Pengembalian"
    const val id_pengembalian = "id_pengembalian"
    val routesWithArg = "$route/{$id_pengembalian}"
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