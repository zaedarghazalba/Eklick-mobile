package com.example.ekclick.model

data class AntriResponse(
    val status: String,  // Status dari API, misalnya "success" atau "error"
    val message: String,  // Pesan dari API, misalnya "Antrian berhasil disimpan"
    val data: AntriData?  // Data antrian yang disimpan (optional)
)

