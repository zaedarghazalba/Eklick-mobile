package com.example.ekclick.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://9c2e-66-96-225-113.ngrok-free.app/api/"
    private const val REKAM_MEDIS_URL = "https://9c2e-66-96-225-113.ngrok-free.app/storage/rekam_medis/"

    val apiService: ApiService by lazy {

        // Set up logging for debugging requests and responses
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Log all request/response bodies

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // Set up Gson with lenient parsing
        val gson = GsonBuilder()
            .setLenient()  // Allow lenient JSON parsing
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use Gson with lenient
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }

    // Method to get the full URL for Rekam Medis
    fun getRekamMedisUrl(rekamMedis: String): String {
        return "$REKAM_MEDIS_URL$rekamMedis"
    }
}
