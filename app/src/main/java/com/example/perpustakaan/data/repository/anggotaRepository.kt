package com.example.perpustakaan.data.repository

import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.data.model.AnggotaResponseDetail
import com.example.perpustakaan.data.service_api.AnggotaService
import kotlinx.coroutines.flow.Flow
import java.io.IOException

interface AnggotaRepository {
    suspend fun getAnggota(): List<Anggota>
    suspend fun insertAnggota(anggota: Anggota)
    suspend fun updateAnggota(idAnggota: Int, anggota: Anggota)
    suspend fun deleteAnggota(idAnggota: Int)
    suspend fun getAnggotaById(idAnggota: Int): AnggotaResponseDetail
}

class NetworkAnggotaRepository(
    private val anggotaApiService: AnggotaService
) : AnggotaRepository {

    // Menambahkan data anggota
    override suspend fun insertAnggota(anggota: Anggota) {
        anggotaApiService.insertAnggota(anggota)
    }

    // Memperbarui data anggota
    override suspend fun updateAnggota(idAnggota: Int, anggota: Anggota) {
        anggotaApiService.updateAnggota(idAnggota, anggota)
    }

    // Menghapus data anggota berdasarkan ID anggota
    override suspend fun deleteAnggota(idAnggota: Int) {
        try {
            val response = anggotaApiService.deleteAnggota(idAnggota)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete anggota. HTTP status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    // Mendapatkan seluruh data anggota
    override suspend fun getAnggota(): List<Anggota> = anggotaApiService.getAnggota().data

    // Mendapatkan detail anggota berdasarkan ID
    override suspend fun getAnggotaById(idAnggota: Int): AnggotaResponseDetail {
        return anggotaApiService.getAnggotaById(idAnggota)
    }
}
