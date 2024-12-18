package com.example.ekclick

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ekclick.api.RetrofitClient.apiService
import com.example.ekclick.model.AntriRequest
import com.example.ekclick.model.AntriResponse
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class VerifDataActivity : AppCompatActivity() {

    private lateinit var btnTanggalLahir: Button
    private lateinit var btnTanggalDaftar: Button
    private lateinit var spinnerGender: Spinner

    private lateinit var edNIK: EditText
    private lateinit var poliNameTextView: TextView
    private lateinit var edFullName: EditText
    private lateinit var edAlamat: EditText
    private lateinit var edNoHp: EditText
    private lateinit var edPekerjaan: EditText
    private lateinit var poli: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verif_data)

        // Mengambil data menuName dari Intent
        poli = intent.getStringExtra("menuName") ?: "Nama Poli Tidak Ditemukan"

        // Menghubungkan TextView untuk menampilkan nama poli
        poliNameTextView = findViewById(R.id.textViewPoliName)
        poliNameTextView.text = poli

        // Inisialisasi tombol dan spinner
        btnTanggalLahir = findViewById(R.id.btnTanggalLahir)
        btnTanggalDaftar = findViewById(R.id.btnTanggalDaftar)
        spinnerGender = findViewById(R.id.spinnerGender)

        // Inisialisasi EditText
        edNIK = findViewById(R.id.edNIK)
        edFullName = findViewById(R.id.edFULLNAME)
        edAlamat = findViewById(R.id.edALAMAT)
        edNoHp = findViewById(R.id.edNOHP)
        edPekerjaan = findViewById(R.id.edPEKERJAAN)

        // Set listener untuk tombol Tanggal Lahir
        btnTanggalLahir.setOnClickListener {
            showDatePicker { selectedDate ->
                val formattedDate = formatDate(selectedDate)
                btnTanggalLahir.text = formattedDate
            }
        }

        // Set listener untuk tombol Tanggal Daftar
        btnTanggalDaftar.setOnClickListener {
            showDatePicker { selectedDate ->
                val formattedDate = formatDate(selectedDate)
                btnTanggalDaftar.text = formattedDate
            }
        }

        // Set Adapter untuk Spinner Jenis Kelamin
        val genderOptions = arrayOf("Laki-laki", "Perempuan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        // Tombol untuk mengirim data
        findViewById<Button>(R.id.SubmitDaftar).setOnClickListener {
            submitData()  // Kirim data ke API
        }
    }

    private fun submitData() {
        // Ambil data dari EditText dan Spinner
        val nik = edNIK.text.toString()
        val fullName = edFullName.text.toString()
        val alamat = edAlamat.text.toString()
        val noHp = edNoHp.text.toString()
        val pekerjaan = edPekerjaan.text.toString()
        val gender = spinnerGender.selectedItem.toString()
        val tanggalLahir = btnTanggalLahir.text.toString()
        val tanggalDaftar = btnTanggalDaftar.text.toString()

        // Pastikan semua field sudah diisi sebelum dikirim
        if (nik.isEmpty() || fullName.isEmpty() || alamat.isEmpty() || noHp.isEmpty() || pekerjaan.isEmpty() || tanggalLahir == "Pilih Tanggal" || tanggalDaftar == "Pilih Tanggal") {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        // Ambil token dari SharedPreferences
        val token = getToken()

        // Pastikan token ada
        if (token == null) {
            Toast.makeText(this, "Token tidak ditemukan. Harap login terlebih dahulu.", Toast.LENGTH_SHORT).show()
            return
        }

        // Buat objek AntriRequest
        val request = AntriRequest(
            tanggal_daftar = tanggalDaftar,
            no_ktp = nik,
            jenis_kelamin = gender,
            no_hp = noHp,
            tgl_lahir = tanggalLahir,
            alamat = alamat,
            nama = fullName,
            pekerjaan = pekerjaan,
            poli = poli,
            rekamMedis = null
        )

        // Kirim data ke API dengan token Authorization
        apiService.sendAntrian("Bearer $token", request).enqueue(object : Callback<AntriResponse> {
            override fun onResponse(call: Call<AntriResponse>, response: Response<AntriResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@VerifDataActivity, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@VerifDataActivity, "Gagal mengirim data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AntriResponse>, t: Throwable) {
                Toast.makeText(this@VerifDataActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getToken(): String? {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPref.getString("access_token", null)  // Ambil token dari SharedPreferences
    }

    private fun showDatePicker(onDateSelected: (Long) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            onDateSelected(selection)
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

    private fun formatDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }
}
