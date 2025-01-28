package com.example.perpustakaan.data.repository

import com.example.perpustakaan.data.model.Pengembalian
import com.example.perpustakaan.data.model.PengembalianResponseDetail
import com.example.perpustakaan.data.service_api.PengembalianService
import java.io.IOException

interface PengembalianRepository {
    suspend fun getPengembalian(): List<Pengembalian>
    suspend fun insertPengembalian(pengembalian: Pengembalian)
    suspend fun updatePengembalian(idPengembalian: Int, pengembalian: Pengembalian)
    suspend fun deletePengembalian(idPengembalian: Int)
    suspend fun getPengembalianById(idPengembalian: Int): PengembalianResponseDetail
}

class NetworkPengembalianRepository(
    private val pengembalianApiService: PengembalianService
) : PengembalianRepository {

    override suspend fun insertPengembalian(pengembalian: Pengembalian) {
        pengembalianApiService.insertPengembalian(pengembalian)
    }

    override suspend fun updatePengembalian(idPengembalian: Int, pengembalian: Pengembalian) {
        pengembalianApiService.updatePengembalian(idPengembalian, pengembalian)
    }

    override suspend fun deletePengembalian(idPengembalian: Int) {
        try {
            val response = pengembalianApiService.deletePengembalian(idPengembalian)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pengembalian. HTTP status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPengembalian(): List<Pengembalian> = pengembalianApiService.getPengembalian().data

    override suspend fun getPengembalianById(idPengembalian: Int): PengembalianResponseDetail {
        return pengembalianApiService.getPengembalianById(idPengembalian)
    }
}
