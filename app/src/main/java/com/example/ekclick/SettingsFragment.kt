package com.example.ekclick

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Handle card clicks
        val card1: CardView = view.findViewById(R.id.card1)
        val card2: CardView = view.findViewById(R.id.card2)
        val card3: CardView = view.findViewById(R.id.card3)
        val card4: CardView = view.findViewById(R.id.card4)

        card1.setOnClickListener {
            // Intent to ProfileActivity (change to your desired activity or fragment)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        card2.setOnClickListener {
            // Intent to DataActivity (change to your desired activity or fragment)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        card3.setOnClickListener {
            // Intent to MedicalRecordsActivity (change to your desired activity or fragment)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        card4.setOnClickListener {
            // Hapus SharedPreferences (jika kamu sebelumnya menyimpan data login)
            val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()  // Menghapus semua data yang disimpan
            editor.apply()

            // Intent untuk mengarahkan ke LoginRegistActivity atau MainActivity
            val intent = Intent(requireContext(), LoginRegistActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Menyelesaikan aktivitas saat ini, sehingga pengguna tidak bisa kembali ke halaman sebelumnya
        }

        return view
    }
}
