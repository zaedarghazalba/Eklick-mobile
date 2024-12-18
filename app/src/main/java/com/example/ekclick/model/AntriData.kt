package com.example.ekclick.model

data class AntriData(
    val userId: String,     // ID pengguna yang membuat antrian
    val poli: String,       // Poli yang dipilih
    val tanggalDaftar: String, // Tanggal pendaftaran
    val nama: String,       // Nama pasien
    val noKtp: String,      // Nomor KTP
    val alamat: String,     // Alamat pasien
    val jenisKelamin: String,  // Jenis kelamin pasien
    val noHp: String,       // Nomor HP pasien
    val tglLahir: String,   // Tanggal lahir pasien
    val pekerjaan: String,  // Pekerjaan pasien
    val rekamMedis: String?,  // URL atau nama file rekam medis yang diupload (optional)
    val no_antrian: String?
)