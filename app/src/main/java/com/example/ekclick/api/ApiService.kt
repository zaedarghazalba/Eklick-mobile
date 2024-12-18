package com.example.ekclick.api

import com.example.ekclick.model.AntriRequest
import com.example.ekclick.model.AntriResponse
import com.example.ekclick.model.AntrianResponse
import com.example.ekclick.model.Antrianmu
import com.example.ekclick.model.Kuota
import com.example.ekclick.model.KuotaResponse
import com.example.ekclick.model.LoginRequest
import com.example.ekclick.model.LoginResponse
import com.example.ekclick.model.RegisterRequest
import com.example.ekclick.model.RegisterResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // For Login - Using query parameters (name, password)
    @POST("login/manual")
    @Headers("Accept: application/json")  // Menambahkan header langsung
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // For Registration - Using query parameters (name, email, password)
    @POST("register/send")  // Ensure the endpoint matches your API's route
    @Headers("Accept: application/json")  // Menambahkan header langsung
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("antrian/send")
    @Headers("Accept: application/json") // Untuk header yang tidak berubah
    fun sendAntrian(
        @Header("Authorization") token: String, // Menambahkan token ke header
        @Body antrianRequest: AntriRequest
    ): Call<AntriResponse>

    @GET("antrian/antrianmu")
    @Headers("Accept: application/json")
    fun getAntrianmu(@Header("Authorization") token: String): Call<AntrianResponse>

    @GET("antrian/kuota")
    @Headers("Accept: application/json")
    fun getKuotaByPoliAndDate(
        @Header("Authorization") token: String,
        @Query("poli") poli: String,
        @Query("tanggal") tanggal: String
    ): Call<KuotaResponse>

}



