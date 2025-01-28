package com.example.perpustakaan.ui.view.buku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.PenyediaViewModel
import com.example.perpustakaan.ui.viewmodel.buku.DetailBukuViewModel

object DestinasiDetailBuku : AlamatNavigasi {
    override val route = "detailbuku"
    override val titleRes = "Detail Buku"
    const val id_buku = "id_buku"
    val routeWithArgs = "$route/{$id_buku}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBukuView(
    idBuku: Int,
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val buku = viewModel.uiState.detailUiEvent

    LaunchedEffect(idBuku) {
        viewModel.fetchDetailBuku(idBuku)
    }

    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailBuku.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    isError -> Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    viewModel.uiState.isUiEventNotEmpty -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    DetailRow(label = "ID Buku", value = buku.id_buku.toString())
                                    DetailRow(label = "Judul", value = buku.judul)
                                    DetailRow(label = "Penulis", value = buku.penulis)
                                    DetailRow(label = "Kategori", value = buku.kategori)
                                    DetailRow(label = "Status", value = buku.status)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ": $value",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}