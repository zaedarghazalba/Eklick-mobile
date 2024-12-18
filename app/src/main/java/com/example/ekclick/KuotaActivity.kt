package com.example.ekclick

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ekclick.api.RetrofitClient
import com.example.ekclick.model.Kuota
import com.example.ekclick.model.KuotaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class KuotaActivity : AppCompatActivity() {

    private lateinit var recyclerViewKuota: RecyclerView
    private lateinit var kuotaAdapter: KuotaAdapter
    private val kuotaList = mutableListOf<Kuota>()
    private lateinit var buttonPilihTanggal: Button
    private lateinit var spinnerPoli: Spinner

    private var selectedPoli: String? = null
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kuota)

        // Inisialisasi RecyclerView dan Adapter
        recyclerViewKuota = findViewById(R.id.recyclerViewKuota)
        recyclerViewKuota.layoutManager = LinearLayoutManager(this)
        kuotaAdapter = KuotaAdapter(kuotaList)
        recyclerViewKuota.adapter = kuotaAdapter

        // Inisialisasi Spinner untuk memilih poli
        spinnerPoli = findViewById(R.id.spinnerPoli)
        val poliList = listOf("Anak", "Umum", "Mata", "Syaraf", "Kulit&Kelamin")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, poliList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPoli.adapter = adapter

        // Menyimpan poli yang dipilih
        spinnerPoli.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Cek apakah position valid
                if (position >= 0 && position < poliList.size) {
                    selectedPoli = poliList[position]  // Menyimpan poli yang dipilih
                    Log.d("KuotaActivity", "Poli yang dipilih: $selectedPoli")
                    // Jika sudah memilih poli dan tanggal, panggil getKuotaByPoliAndDate
                    if (selectedDate != null) {
                        getKuotaByPoliAndDate(selectedPoli!!, selectedDate!!)
                    }
                } else {
                    Log.e("KuotaActivity", "Invalid position selected")
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Tidak perlu apa-apa jika tidak ada yang dipilih
            }
        }

        // Inisialisasi button untuk memilih tanggal
        buttonPilihTanggal = findViewById(R.id.buttonPilihTanggal)
        buttonPilihTanggal.setOnClickListener {
            showDatePickerDialog()
        }
    }

    // Fungsi untuk mendapatkan kuota berdasarkan poli dan tanggal
    private fun getKuotaByPoliAndDate(poli: String, tanggal: String) {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val token = sharedPref.getString("access_token", null)

        if (token != null) {
            val tokenString = "Bearer $token"

            // Memanggil API untuk mendapatkan kuota berdasarkan poli dan tanggal
            RetrofitClient.apiService.getKuotaByPoliAndDate(tokenString, poli, tanggal).enqueue(object : Callback<KuotaResponse> {
                override fun onResponse(call: Call<KuotaResponse>, response: Response<KuotaResponse>) {
                    if (response.isSuccessful) {
                        val kuotaResponse = response.body()
                        kuotaResponse?.let {
                            if (it.data.isEmpty()) {
                                Toast.makeText(this@KuotaActivity, "Tidak ada kuota untuk poli dan tanggal tersebut.", Toast.LENGTH_SHORT).show()
                            } else {
                                kuotaList.clear()
                                kuotaList.addAll(it.data)  // Menambahkan data dari `it.data`
                                kuotaAdapter.notifyDataSetChanged()
                            }
                        }
                    } else {
                        Log.e("KuotaActivity", "Error: ${response.message()}")
                        Toast.makeText(this@KuotaActivity, "Gagal memuat data kuota.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<KuotaResponse>, t: Throwable) {
                    Log.e("KuotaActivity", "Error: ${t.message}")
                    Toast.makeText(this@KuotaActivity, "Gagal mengambil data: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Log.e("KuotaActivity", "Token tidak ditemukan")
            Toast.makeText(this@KuotaActivity, "Pengguna belum login.", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk menampilkan DatePickerDialog
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                // Jika sudah memilih tanggal dan poli, panggil getKuotaByPoliAndDate
                if (selectedPoli != null) {
                    getKuotaByPoliAndDate(selectedPoli!!, selectedDate!!)
                }
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}
