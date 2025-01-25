package com.example.perpustakaan.data.repository

import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.data.model.AnggotaResponseDetail

interface AnggotaRepository {
    suspend fun getAnggota(): List<Anggota>
    suspend fun insertAnggota(anggota: Anggota)
    suspend fun updateAnggota(idAnggota: Int, anggota: Anggota)
    suspend fun deleteAnggota(idAnggota: Int)
    suspend fun getAnggotaById(idAnggota: Int): AnggotaResponseDetail
}