package com.example.ekclick.model

data class Antrianmu(
    val id: Int,
    val poli: String,
    val nama: String,
    val no_ktp: String,
    val alamat: String,
    val jenis_kelamin: String,
    val no_hp: String,
    val tanggal_daftar: String,  // Pastikan ini bertipe String
    val tgl_lahir: String,
    val pekerjaan: String,
    val rekam_medis: String?,
    val user_id: Int,
    val created_at: String,
    val updated_at: String,
    val no_antrian: Int
)
