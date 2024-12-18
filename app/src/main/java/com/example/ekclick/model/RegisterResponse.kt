package com.example.ekclick.model

data class RegisterResponse(
    val status: String,
    val message: String,
    val user: User
)