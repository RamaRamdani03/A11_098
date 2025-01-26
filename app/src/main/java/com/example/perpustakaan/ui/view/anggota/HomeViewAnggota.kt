package com.example.perpustakaan.ui.view.anggota

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.R
import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.ui.customwidget.CostumeTopAppBar
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.PenyediaViewModel
import com.example.perpustakaan.ui.viewmodel.anggota.HomeUiState
import com.example.perpustakaan.ui.viewmodel.anggota.HomeViewModelAnggota

object DestinasiHomeAnggota : AlamatNavigasi {
    override val route = "homeAnggota"
    override val titleRes = "Anggota Perpustakaan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAnggota(
    navigateToItemAnggotaEntry: () -> Unit,
    navigateBack: () -> Unit,
    navigateToEditAnggota: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDetailAnggotaClick: (Int) -> Unit = {},
    viewModel: HomeViewModelAnggota = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeAnggota.titleRes,
                canNavigateBack = true,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAnggota()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemAnggotaEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Anggota")
            }
        },
    ) { innerPadding ->
        HomeStatusAnggota(
            homeUiState = viewModel.anggotaUIState,
            retryAction = { viewModel.getAnggota() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailAnggotaClick,
            onDeleteClick = {
                viewModel.deleteAnggota(it.id_anggota)
                viewModel.getAnggota()
            },
            onEditAnggota = { id_anggota -> navigateToEditAnggota(id_anggota) }
        )
    }
}

@Composable
fun HomeStatusAnggota(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onEditAnggota: (Int) -> Unit,
    onDeleteClick: (Anggota) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.anggota.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada anggota")
                }
            } else {
                AnggotaLayout(
                    anggota = homeUiState.anggota,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_anggota) },
                    onDeleteClick = { onDeleteClick(it) },
                    onEditAnggota = onEditAnggota
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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

@Composable
fun AnggotaLayout(
    anggota: List<Anggota>,
    modifier: Modifier = Modifier,
    onDetailClick: (Anggota) -> Unit,
    onEditAnggota: (Int) -> Unit,
    onDeleteClick: (Anggota) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(anggota) { member ->
            AnggotaCard(
                anggota = member,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(member) },
                onDeleteClick = { onDeleteClick(member) },
                onEditAnggota = onEditAnggota
            )
        }
    }
}

@Composable
fun AnggotaCard(
    anggota: Anggota,
    modifier: Modifier = Modifier,
    onDeleteClick: (Anggota) -> Unit = {},
    onEditAnggota: (Int) -> Unit
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
                Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ID: ${anggota.id_anggota}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Face, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = anggota.nama,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                //Delete
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }

                //Edit Button
                IconButton(onClick = { onEditAnggota(anggota.id_anggota) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Buku")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = anggota.email,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Call, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = anggota.nomor_telepon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text("Apakah anda yakin ingin menghapus data anggota ini?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick(anggota)
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
