package com.example.ekclick

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Delay selama 3 detik sebelum berpindah ke LoginRegistActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginRegistActivity::class.java)
            startActivity(intent)
            finish() // Mengakhiri SplashScreen activity agar tidak dapat kembali dengan tombol back
        }, 3000) // Waktu delay dalam milidetik
    }
}
