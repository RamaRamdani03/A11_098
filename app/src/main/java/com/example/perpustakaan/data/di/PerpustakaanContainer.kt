package com.example.perpustakaan.data.di

import com.example.perpustakaan.data.repository.AnggotaRepository
import com.example.perpustakaan.data.repository.NetworkAnggotaRepository
import com.example.perpustakaan.data.service_api.AnggotaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val anggotaRepository : AnggotaRepository
}

class PerpustakaanContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3001/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val anggotaService: AnggotaService by lazy {
        retrofit.create(AnggotaService::class.java)
    }

    override val anggotaRepository: AnggotaRepository by lazy {
        NetworkAnggotaRepository(anggotaService)
    }
}