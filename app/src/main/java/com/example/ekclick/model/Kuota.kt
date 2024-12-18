package com.example.ekclick.model

// Model untuk response API
data class KuotaResponse(
    val status: String,
    val message: String,
    val data: List<Kuota>
)

// Model untuk Kuota
data class Kuota(
    val no_antrian: Int,
    val poli: String,
    val tanggal_daftar: String,
    val nama: String
)
