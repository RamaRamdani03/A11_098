package com.example.perpustakaan.data.repository

import com.example.perpustakaan.data.model.Buku
import com.example.perpustakaan.data.model.BukuResponseDetail
import com.example.perpustakaan.data.service_api.BukuService
import java.io.IOException

interface BukuRepository {
    suspend fun getBuku(): List<Buku>
    suspend fun insertBuku(buku: Buku)
    suspend fun updateBuku(idBuku: Int, buku: Buku)
    suspend fun deleteBuku(idBuku: Int)
    suspend fun getBukuById(idBuku: Int): BukuResponseDetail
}

class NetworkBukuRepository(
    private val bukuApiService: BukuService
) : BukuRepository {
    override suspend fun insertBuku(buku: Buku) {
        bukuApiService.insertBuku(buku)
    }

    override suspend fun updateBuku(idBuku: Int, buku: Buku) {
        bukuApiService.updateBuku(idBuku, buku)
    }

    override suspend fun deleteBuku(idBuku: Int) {
        try {
            val response = bukuApiService.deleteBuku(idBuku)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete buku. HTTP status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBuku(): List<Buku> = bukuApiService.getBuku().data

    override suspend fun getBukuById(idBuku: Int): BukuResponseDetail {
        return bukuApiService.getBukuById(idBuku)
    }
}
