package com.example.perpustakaan.data.service_api

import com.example.perpustakaan.data.model.Peminjaman
import com.example.perpustakaan.data.model.PeminjamanResponse
import com.example.perpustakaan.data.model.PeminjamanResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PeminjamanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    // Mendapatkan seluruh data peminjaman
    @GET("peminjaman")
    suspend fun getPeminjaman(): PeminjamanResponse

    // Mendapatkan detail peminjaman berdasarkan ID
    @GET("peminjaman/{id_peminjaman}")
    suspend fun getPeminjamanById(@Path("id_peminjaman") idPeminjaman: Int): PeminjamanResponseDetail

    // Menambahkan data peminjaman baru
    @POST("peminjaman/peminjaman")
    suspend fun insertPeminjaman(@Body peminjaman: Peminjaman)

    // Memperbarui data peminjaman berdasarkan ID
    @PUT("peminjaman/{id_peminjaman}")
    suspend fun updatePeminjaman(@Path("id_peminjaman") idPeminjaman: Int, @Body peminjaman: Peminjaman)

    // Menghapus peminjaman berdasarkan ID
    @DELETE("peminjaman/{id_peminjaman}")
    suspend fun deletePeminjaman(@Path("id_peminjaman") idPeminjaman: Int): Response<Void>
}