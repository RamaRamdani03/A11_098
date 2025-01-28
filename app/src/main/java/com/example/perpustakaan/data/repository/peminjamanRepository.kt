package com.example.perpustakaan.data.repository

import com.example.perpustakaan.data.model.Peminjaman
import com.example.perpustakaan.data.model.PeminjamanResponseDetail
import com.example.perpustakaan.data.service_api.PeminjamanService
import java.io.IOException

interface PeminjamanRepository {
    suspend fun getPeminjaman(): List<Peminjaman>
    suspend fun insertPeminjaman(peminjaman: Peminjaman)
    suspend fun updatePeminjaman(idPeminjaman: Int, peminjaman: Peminjaman)
    suspend fun deletePeminjaman(idPeminjaman: Int)
    suspend fun getPeminjamanById(idPeminjaman: Int): PeminjamanResponseDetail
}

class NetworkPeminjamanRepository(
    private val peminjamanApiService: PeminjamanService
) : PeminjamanRepository {

    override suspend fun insertPeminjaman(peminjaman: Peminjaman) {
        peminjamanApiService.insertPeminjaman(peminjaman)
    }

    override suspend fun updatePeminjaman(idPeminjaman: Int, peminjaman: Peminjaman) {
        peminjamanApiService.updatePeminjaman(idPeminjaman, peminjaman)
    }

    override suspend fun deletePeminjaman(idPeminjaman: Int) {
        try {
            val response = peminjamanApiService.deletePeminjaman(idPeminjaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete peminjaman. HTTP status code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPeminjaman(): List<Peminjaman> {
        return try {
            val response = peminjamanApiService.getPeminjaman()

            if (response.status) {
                response.data
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getPeminjamanById(idPeminjaman: Int): PeminjamanResponseDetail {
        return peminjamanApiService.getPeminjamanById(idPeminjaman)
    }
}
