package com.example.ekclick

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ekclick.api.RetrofitClient
import com.example.ekclick.model.AntrianResponse
import com.example.ekclick.model.Antrianmu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AntrianFragment : Fragment() {

    private lateinit var recyclerViewAntrian: RecyclerView
    private lateinit var antrianAdapter: AntrianAdapter
    private var antrianList: MutableList<Antrianmu> = mutableListOf()  // Inisialisasi list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_antrian, container, false)

        // Inisialisasi RecyclerView dan Adapter
        recyclerViewAntrian = binding.findViewById(R.id.recyclerViewAntrian)
        recyclerViewAntrian.layoutManager = LinearLayoutManager(context)

        antrianAdapter = AntrianAdapter(antrianList)
        recyclerViewAntrian.adapter = antrianAdapter

        // Memanggil API untuk mengambil data antrian
        getAntrian()  // Panggil fungsi getAntrian

        return binding
    }

    fun getAntrian() {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val token = sharedPref.getString("access_token", null)

        if (token != null) {
            val apiService = RetrofitClient.apiService
            val tokenString = "Bearer $token"

            apiService.getAntrianmu(tokenString).enqueue(object : Callback<AntrianResponse> {
                override fun onResponse(call: Call<AntrianResponse>, response: Response<AntrianResponse>) {
                    if (response.isSuccessful) {
                        val antrianResponse = response.body()
                        antrianResponse?.let {
                            if (it.data.isEmpty()) {
                                // Jika data kosong, tampilkan Toast
                                Toast.makeText(requireContext(), "Anda belum memiliki antrian.", Toast.LENGTH_SHORT).show()
                            } else {
                                // Jika ada data, update RecyclerView
                                antrianAdapter.updateData(it.data) // Mengupdate RecyclerView dengan data
                            }
                        }
                    } else {
                        Log.e("AntrianFragment", "Error: ${response.message()}")
                        Toast.makeText(requireContext(), "Failed to load data.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AntrianResponse>, t: Throwable) {
                    Log.e("AntrianFragment", "Error: ${t.message}")
                    Toast.makeText(requireContext(), "Error fetching data: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Log.e("AntrianFragment", "Token not found")
            Toast.makeText(requireContext(), "User is not logged in.", Toast.LENGTH_SHORT).show()
        }
    }
}