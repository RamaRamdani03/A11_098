package com.example.perpustakaan.data.service_api

import com.example.perpustakaan.data.model.Buku
import com.example.perpustakaan.data.model.BukuResponse
import com.example.perpustakaan.data.model.BukuResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BukuService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    // Mendapatkan daftar buku
    @GET("buku")
    suspend fun getBuku(): BukuResponse

    // Mendapatkan buku berdasarkan ID
    @GET("buku/{id_buku}")
    suspend fun getBukuById(@Path("id_buku") idBuku: Int): BukuResponseDetail

    // Menambahkan buku baru
    @POST("buku/buku")
    suspend fun insertBuku(@Body buku: Buku)

    // Memperbarui data buku
    @PUT("buku/{id_buku}")
    suspend fun updateBuku(@Path("id_buku") idBuku: Int, @Body buku: Buku)

    // Menghapus buku
    @DELETE("buku/{id_buku}")
    suspend fun deleteBuku(@Path("id_buku") idBuku: Int): Response<Void>
}