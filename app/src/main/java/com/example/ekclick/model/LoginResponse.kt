package com.example.ekclick.model

data class LoginResponse(
    val status: Boolean,  // For the "status" field
    val code: Int,        // For the "code" field
    val message: String,  // For the "message" field
    val data: LoginData   // For the "data" object
)

data class LoginData(
    val access_token: String,  // For the "access_token"
    val token_type: String,    // For the "token_type"
    val expires_in: Int        // For the "expires_in"
)