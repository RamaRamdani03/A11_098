package com.example.perpustakaan.ui.view.pengembalian

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.R
import com.example.perpustakaan.data.ListPeminjaman.tanggalKembali
import com.example.perpustakaan.ui.customwidget.CostumeTopAppBar
import com.example.perpustakaan.ui.viewmodel.pengembalian.HomeViewModelPengembalian
import com.example.perpustakaan.ui.viewmodel.pengembalian.PengembalianUiState
import com.example.perpustakaan.data.model.Pengembalian
import com.example.perpustakaan.ui.navigasi.AlamatNavigasi
import com.example.perpustakaan.ui.viewmodel.PenyediaViewModel
import com.example.perpustakaan.ui.viewmodel.peminjaman.HomeViewModelPeminjaman
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DestinasiHomePengembalian : AlamatNavigasi {
    override val route = "homePengembalian"
    override val titleRes = "Data Pengembalian"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPengembalian(
    navigateToItemPengembalianEntry: () -> Unit,
    navigateBack: () -> Unit,
    navigateToEditPengembaliann: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDetailPengembalianClick: (Int) -> Unit = {},
    viewModel: HomeViewModelPengembalian = viewModel(factory = PenyediaViewModel.Factory),
    tglKembaliViewModel : HomeViewModelPeminjaman = viewModel(factory = PenyediaViewModel.Factory),
) {
    val dataKembali = tanggalKembali(tglKembaliViewModel)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePengembalian.titleRes,
                canNavigateBack = true,
                showRefresh = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getPengembalian() },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemPengembalianEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pengembalian")
            }
        }
    ) { innerPadding ->
        HomeStatusPengembalian(
            pengembalianUiState = viewModel.pengembalianUiState.value,
            retryAction = { viewModel.getPengembalian() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailPengembalianClick,
            onDeleteClick = {
                viewModel.deletePengembalian(it.id_pengembalian)
                viewModel.getPengembalian()
            },
            onEditPengembalian = { id_pengembalian ->
                navigateToEditPengembaliann(id_pengembalian)
            },
            dataTglList = dataKembali
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeStatusPengembalian(
    pengembalianUiState: PengembalianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pengembalian) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditPengembalian: (Int) -> Unit,
    dataTglList: List<Pair<String, Int>>
) {
    when (pengembalianUiState) {
        is PengembalianUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PengembalianUiState.Success -> {
            if (pengembalianUiState.pengembalian.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data pengembalian")
                }
            } else {
                PengembalianLayout(
                    pengembalian = pengembalianUiState.pengembalian,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_pengembalian) },
                    onDeleteClick = { onDeleteClick(it) },
                    onEditPengembaliann = onEditPengembalian,
                    dataTglList = dataTglList
                )
            }
        }
        is PengembalianUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PengembalianLayout(
    pengembalian: List<Pengembalian>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pengembalian) -> Unit,
    onDeleteClick: (Pengembalian) -> Unit = {},
    onEditPengembaliann: (Int) -> Unit = {},
    dataTglList: List<Pair<String, Int>>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pengembalian) { item ->
            val dataKembali = dataTglList.find { it.second == item.id_peminjaman }?.first ?: "Tanggal tidak ditemukan"
            PengembalianCard(
                pengembalian = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) },
                onEditPengembaliann = onEditPengembaliann,
                tglKembali = dataKembali
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PengembalianCard(
    pengembalian: Pengembalian,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pengembalian) -> Unit = {},
    onEditPengembaliann: (Int) -> Unit = {},
    tglKembali: String
) {
    Log.d("PengembalianCard", "ID Peminjaman: ${pengembalian.id_peminjaman}, Tgl Kembali: $tglKembali, Tgl Dikembalikan: ${pengembalian.tanggal_dikembalikan}")

    var denda = 0
    try {
        val dateKembali = parseTanggal(tglKembali)
        val dateDikembalikan = parseTanggal(pengembalian.tanggal_dikembalikan)

        if (dateKembali != null && dateDikembalikan != null && !dateDikembalikan.isBefore(dateKembali)) {
            val daysLate = ChronoUnit.DAYS.between(dateKembali, dateDikembalikan).toInt()
            denda = daysLate * 1000
            Log.d("PengembalianCard", "Days Late: $daysLate, Denda: $denda")
        } else {
            Log.d("PengembalianCard", "Tidak ada keterlambatan atau tanggal tidak valid.")
        }
    } catch (e: Exception) {
        Log.e("PengembalianCard", "Error parsing dates or calculating denda: ${e.message}")
    }

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
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ID Pengembalian: ${pengembalian.id_pengembalian}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Face, contentDescription = "Icon Nama Anggota")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Nama Anggota: ${pengembalian.nama}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.bukuuuuuu),
                    contentDescription = "Icon Judul",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Judul: ${pengembalian.judul}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Icon Tanggal Kembali")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Tanggal Kembali: $tglKembali")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Icon Tanggal Dikembalikan")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Tanggal Dikembalikan: ${pengembalian.tanggal_dikembalikan}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.iconuangnobackground),
                    contentDescription = "Icon Denda",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Denda: Rp $denda")
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onEditPengembaliann(pengembalian.id_pengembalian) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Pengembalian"
                    )
                }
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Pengembalian"
                    )
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus data pengembalian ini?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick(pengembalian)
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