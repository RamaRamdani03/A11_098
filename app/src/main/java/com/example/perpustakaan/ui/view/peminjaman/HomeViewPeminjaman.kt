package com.example.perpustakaan.ui.view.peminjaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.perpustakaan.R
import com.example.perpustakaan.ui.viewmodel.peminjaman.PeminjamanUiState
import com.example.perpustakaan.data.model.Peminjaman
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi

object DestinasiHomePeminjaman : AlamatNavigasi {
    override val route = "homePeminjaman"
    override val titleRes = "Data Peminjaman"
}

@Composable
fun HomeStatusPeminjaman(
    peminjamanUiState: PeminjamanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peminjaman) -> Unit = {},
    onEditPeminjaman: (Int) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    when (peminjamanUiState) {
        is PeminjamanUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PeminjamanUiState.Success -> {
            // Filter peminjaman yang statusnya "Dipinjam" atau yang belum dikembalikan
            val peminjamanYangBelumDikembalikan = peminjamanUiState.peminjaman.filter { it.status == "Di Pinjam" }

            if (peminjamanYangBelumDikembalikan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada peminjaman yang belum dikembalikan")
                }
            } else {
                PeminjamanLayout(
                    peminjaman = peminjamanYangBelumDikembalikan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_peminjaman) },
                    onDeleteClick = { onDeleteClick(it) },
                    onEditPeminjaman = onEditPeminjaman
                )
            }
        }
        is PeminjamanUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PeminjamanLayout(
    peminjaman: List<Peminjaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (Peminjaman) -> Unit,
    onEditPeminjaman: (Int) -> Unit,
    onDeleteClick: (Peminjaman) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(peminjaman) { item ->
            PeminjamanCard(
                peminjaman = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) },
                onEditPeminjaman = onEditPeminjaman
            )
        }
    }
}

@Composable
fun PeminjamanCard(
    peminjaman: Peminjaman,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peminjaman) -> Unit = {},
    onEditPeminjaman: (Int) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.AccountBox, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ID Peminjaman: ${peminjaman.id_peminjaman}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.Face,
                    contentDescription = "Nama Anggota"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Nama Anggota: ${peminjaman.nama}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.bukuuuuuu),
                    contentDescription = "Judul Buku",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Judul Buku: ${peminjaman.judul}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.DateRange,
                    contentDescription = "Tanggal Peminjaman"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Tanggal Peminjaman: ${peminjaman.tanggal_peminjaman}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.DateRange,
                    contentDescription = "Tanggal Pengembalian"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Tanggal Pengembalian: ${peminjaman.tanggal_pengembalian}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Status"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Status: ${peminjaman.status}")
            }

                Spacer(Modifier.weight(1f))

            // Tombol Delete
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Peminjaman"
                    )
                }

                // Edit
                IconButton(onClick = { onEditPeminjaman(peminjaman.id_peminjaman) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Peminjaman"
                    )
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data peminjaman ini?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick(peminjaman)
                    showDialog.value = false
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Tidak")
                }
            }
        )
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