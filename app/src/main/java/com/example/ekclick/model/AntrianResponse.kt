package com.example.ekclick.model

data class AntrianResponse(
    val status: String,
    val message: String,
    val data: List<Antrianmu> // The actual data is in the "data" field
)