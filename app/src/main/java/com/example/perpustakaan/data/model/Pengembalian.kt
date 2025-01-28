package com.example.perpustakaan.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pengembalian(
    val id_pengembalian: Int = 0,

    @Serializable
    val id_peminjaman: Int = 0,

    val nama:String = "",
    val judul:String = "",

    @SerialName("tanggal_dikembalikan")
    val tanggal_dikembalikan: String, // Format: "YYYY-MM-DD"

)

@Serializable
data class PengembalianResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pengembalian>
)

@Serializable
data class PengembalianResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Pengembalian
)
