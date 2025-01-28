package com.example.perpustakaan.data.di

import com.example.perpustakaan.data.repository.AnggotaRepository
import com.example.perpustakaan.data.repository.BukuRepository
import com.example.perpustakaan.data.repository.NetworkAnggotaRepository
import com.example.perpustakaan.data.repository.NetworkBukuRepository
import com.example.perpustakaan.data.repository.NetworkPeminjamanRepository
import com.example.perpustakaan.data.repository.NetworkPengembalianRepository
import com.example.perpustakaan.data.repository.PeminjamanRepository
import com.example.perpustakaan.data.repository.PengembalianRepository
import com.example.perpustakaan.data.service_api.AnggotaService
import com.example.perpustakaan.data.service_api.BukuService
import com.example.perpustakaan.data.service_api.PeminjamanService
import com.example.perpustakaan.data.service_api.PengembalianService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val anggotaRepository : AnggotaRepository
    val bukuRepository : BukuRepository
    val peminjamanRepository : PeminjamanRepository
    val pengembalianRepository : PengembalianRepository
}

class PerpustakaanContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3001/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val bukuService: BukuService by lazy {
        retrofit.create(BukuService::class.java)
    }

    private val anggotaService: AnggotaService by lazy {
        retrofit.create(AnggotaService::class.java)
    }

    private val peminjamanService: PeminjamanService by lazy {
        retrofit.create(PeminjamanService::class.java)
    }

    private val pengembalianService: PengembalianService by lazy {
        retrofit.create(PengembalianService::class.java)
    }

    override val anggotaRepository: AnggotaRepository by lazy {
        NetworkAnggotaRepository(anggotaService)
    }

    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }

    override val peminjamanRepository: PeminjamanRepository by lazy {
        NetworkPeminjamanRepository(peminjamanService)
    }

    override val pengembalianRepository: PengembalianRepository by lazy {
        NetworkPengembalianRepository(pengembalianService)
    }
}