package com.example.perpustakaan.data.service_api

import com.example.perpustakaan.data.model.Pengembalian
import com.example.perpustakaan.data.model.PengembalianResponse
import com.example.perpustakaan.data.model.PengembalianResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PengembalianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    // Mendapatkan seluruh data pengembalian
    @GET("pengembalian")
    suspend fun getPengembalian(): PengembalianResponse

    // Mendapatkan detail pengembalian berdasarkan ID peminjaman
    @GET("pengembalian/{id_pengembalian}")
    suspend fun getPengembalianById(@Path("id_pengembalian") idPengembalian: Int): PengembalianResponseDetail

    // Menambahkan data pengembalian baru
    @POST("pengembalian/pengembalian")
    suspend fun insertPengembalian(@Body pengembalian: Pengembalian)

    // Memperbarui data pengembalian berdasarkan ID peminjaman
    @PUT("pengembalian/{id_pengembalian}")
    suspend fun updatePengembalian(@Path("id_pengembalian") idPengembalian: Int, @Body pengembalian: Pengembalian)

    // Menghapus pengembalian berdasarkan ID pengembalian
    @DELETE("pengembalian/{id_pengembalian}")
    suspend fun deletePengembalian(@Path("id_pengembalian") idPengembalian: Int): Response<Void>
}
