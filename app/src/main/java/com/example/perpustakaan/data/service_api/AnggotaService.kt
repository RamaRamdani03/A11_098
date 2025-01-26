package com.example.perpustakaan.data.service_api

import com.example.perpustakaan.data.model.Anggota
import com.example.perpustakaan.data.model.AnggotaResponse
import com.example.perpustakaan.data.model.AnggotaResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnggotaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    // Mendapatkan daftar anggota
    @GET("anggota")
    suspend fun getAnggota(): AnggotaResponse

    // Mendapatkan anggota berdasarkan ID
    @GET("anggota/{id}")
    suspend fun getAnggotaById(@Path("id") id: Int): AnggotaResponseDetail

    // Menambahkan anggota baru
    @POST("anggota/anggota")
    suspend fun insertAnggota(@Body anggota: Anggota): Response<Unit>

    // Memperbarui data anggota
    @PUT("anggota/{id}")
    suspend fun updateAnggota(@Path("id") id: Int, @Body anggota: Anggota)

    // Menghapus anggota
    @DELETE("anggota/{id}")
    suspend fun deleteAnggota(@Path("id") id: Int): Response<Void>
}
