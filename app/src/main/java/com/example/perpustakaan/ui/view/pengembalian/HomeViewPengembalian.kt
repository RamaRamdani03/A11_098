package com.example.perpustakaan.ui.view.pengembalian

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.perpustakaan.R
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DestinasiHomePengembalian : AlamatNavigasi {
    override val route = "homePengembalian"
    override val titleRes = "Data Pengembalian"
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseTanggal(tanggal: String): LocalDate? {
    return try {
        val formattedTanggal = tanggal.split("T")[0] // Mengambil bagian 'yyyy-MM-dd'
        LocalDate.parse(formattedTanggal, DateTimeFormatter.ISO_DATE)
    } catch (e: Exception) {
        Log.e("parseTanggal", "Failed to parse date: $tanggal, error: ${e.message}")
        null
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(64.dp),
        painter = painterResource(R.drawable.iconloading),
        contentDescription = "Loading"
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Terjadi kesalahan saat mengambil data")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = retryAction) {
            Text(text = "Coba Lagi")
        }
    }
}