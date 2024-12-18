package com.example.ekclick.model

data class AntriRequest(
    val tanggal_daftar: String,
    val no_ktp: String,
    val jenis_kelamin: String,
    val no_hp: String,
    val tgl_lahir: String,
    val alamat: String,
    val nama: String,
    val pekerjaan: String,
    val poli: String,
    val rekamMedis: String?
)