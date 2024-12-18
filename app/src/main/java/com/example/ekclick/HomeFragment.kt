package com.example.ekclick

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var menuItemList: MutableList<String>
    private lateinit var CekKuota: CardView
    private lateinit var recyclerViewPengumuman: RecyclerView
    private lateinit var textViewUsername: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout untuk fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi TextView untuk Menampilkan Username
        textViewUsername = view.findViewById(R.id.tvUsername)

        // Mengambil username dari SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", AppCompatActivity.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Guest") // Default to "Guest" if not found
        textViewUsername.text = "Welcome, $username"

        // Inisialisasi RecyclerView untuk Pengumuman
        recyclerViewPengumuman = view.findViewById(R.id.recyclerViewPengumuman)

        // Inisialisasi CardView untuk Cek Kuota
        CekKuota = view.findViewById(R.id.cardViewCekKuota)
        CekKuota.setOnClickListener {
            val intent = Intent(requireContext(), KuotaActivity::class.java)
            startActivity(intent)
            // Tambahkan logika untuk membuka halaman cek kuota di sini
        }

        // Inisialisasi RecyclerView Menu
        recyclerViewMenu = view.findViewById(R.id.recyclerViewMenu)

        // Inisialisasi dan isi daftar menu
        menuItemList = mutableListOf()
        populateMenuItems()

        // Set up adapter untuk Menu
        menuAdapter = MenuAdapter(requireContext(), menuItemList)

        // Set LayoutManager untuk RecyclerView Menu
        recyclerViewMenu.layoutManager = LinearLayoutManager(requireContext())

        // Set adapter ke RecyclerView Menu
        recyclerViewMenu.adapter = menuAdapter

        // Set LayoutManager untuk RecyclerView Pengumuman dengan orientasi horizontal
        recyclerViewPengumuman.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Inisialisasi dan set adapter untuk RecyclerView Pengumuman
        val pengumumanList = listOf(
            "Pengumuman 1: Jadwal perawatan akan berubah.",
            "Pengumuman 2: Libur nasional pada hari Senin.",
            "Pengumuman 3: Pendaftaran dibuka pada tanggal 1 Januari."
        )
        val pengumumanAdapter = PengumumanAdapter(pengumumanList)
        recyclerViewPengumuman.adapter = pengumumanAdapter

        return view
    }

    private fun populateMenuItems() {
        // Menambahkan item ke dalam daftar
        menuItemList.add("Ibu Dan Anak")
        menuItemList.add("Umum")
        menuItemList.add("THT")
        menuItemList.add("Syaraf")
        menuItemList.add("Kulit Dan Kelamin")
        menuItemList.add("Mata")
    }
}
