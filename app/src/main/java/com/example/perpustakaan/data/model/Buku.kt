package com.example.perpustakaan.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Buku(
    val id_buku: Int = 0,
    val judul: String,
    val penulis: String,
    val kategori: String,
    val status: String
)

@Serializable
data class BukuResponse(
    val status: Boolean,
    val message: String,
    val data: List<Buku>
)

@Serializable
data class BukuResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Buku
)
