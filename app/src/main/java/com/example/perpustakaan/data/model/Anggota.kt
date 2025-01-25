package com.example.perpustakaan.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Anggota(
    val id_anggota: Int = 0,
    val nama: String,
    val email: String,
    val nomor_telepon: String
)