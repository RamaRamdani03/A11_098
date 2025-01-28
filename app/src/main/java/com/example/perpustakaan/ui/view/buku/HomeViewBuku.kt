package com.example.perpustakaan.ui.view.buku

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.perpustakaan.R
import com.example.perpustakaan.data.model.Buku
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.buku.BukuUiState

object DestinasiHomeBuku : AlamatNavigasi {
    override val route = "Home Buku"
    override val titleRes = "Menu Buku"
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.TextField(
            value = query,
            onValueChange = onQueryChanged,
            label = { Text("Cari buku") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun HomeStatusBuku(
    bukuUiState: BukuUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Buku) -> Unit = {},
    onEditBuku: (Int) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    when (bukuUiState) {
        is BukuUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is BukuUiState.Success -> {
            if (bukuUiState.buku.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada buku")
                }
            } else {
                BukuLayout(
                    buku = bukuUiState.buku,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_buku) },
                    onDeleteClick = { onDeleteClick(it) },
                    onEditBuku = onEditBuku
                )
            }
        }
        is BukuUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun BukuLayout(
    buku: List<Buku>,
    modifier: Modifier = Modifier,
    onDetailClick: (Buku) -> Unit,
    onEditBuku: (Int) -> Unit,
    onDeleteClick: (Buku) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(buku) { book ->
            BukuCard(
                buku = book,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(book) },
                onDeleteClick = { onDeleteClick(book) },
                onEditBuku = onEditBuku
            )
        }
    }
}

@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier,
    onDeleteClick: (Buku) -> Unit = {},
    onEditBuku: (Int) -> Unit
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "ID Buku", modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ID: ${buku.id_buku}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.bukuuuuuu),
                    contentDescription = "Judul Buku",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = buku.judul, style = MaterialTheme.typography.titleLarge)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.penuliswkwk),
                    contentDescription = "Penulis",
                    modifier = Modifier.size(30.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Penulis: ${buku.penulis}", style = MaterialTheme.typography.bodyMedium)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.namanyakategori),
                    contentDescription = "Kategori",
                    modifier = Modifier.size(30.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Kategori: ${buku.kategori}", style = MaterialTheme.typography.bodyMedium)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Status",
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Status: ${buku.status}", style = MaterialTheme.typography.bodyMedium)
            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.weight(1f))

                // Icon for Delete
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Buku"
                    )
                }

                // Edit Button
                IconButton(
                    onClick = { onEditBuku(buku.id_buku) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Buku"
                    )
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text("Apakah anda yakin ingin menghapus buku ini?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick(buku)
                    showDialog.value = false
                } )
                {
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
        modifier = modifier.size(8.dp),
        painter = painterResource(R.drawable.iconloading),
        contentDescription = stringResource(R.string.loading)
    )
}
@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.erroricon),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}