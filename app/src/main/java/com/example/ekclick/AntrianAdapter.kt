package com.example.ekclick

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ekclick.api.RetrofitClient
import com.example.ekclick.model.Antrianmu

class AntrianAdapter(private var antrianList: List<Antrianmu>) :
    RecyclerView.Adapter<AntrianAdapter.AntrianViewHolder>() {

    // Creates new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AntrianViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_antrian, parent, false)
        return AntrianViewHolder(view)
    }

    // Binds data to ViewHolder
    override fun onBindViewHolder(holder: AntrianViewHolder, position: Int) {
        val antrian = antrianList[position]
        holder.bind(antrian)
    }

    // Returns the size of the data list
    override fun getItemCount(): Int = antrianList.size

    // Function to update data in the adapter
    fun updateData(newData: List<Antrianmu>) {
        antrianList = newData
        notifyDataSetChanged() // Notify RecyclerView that the data has changed
    }

    // ViewHolder class to hold item views
    class AntrianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poliTextView: TextView = itemView.findViewById(R.id.textPoli)
        private val namaTextView: TextView = itemView.findViewById(R.id.textNama)
        private val tanggalTextView: TextView = itemView.findViewById(R.id.textTanggal)
        private val noAntrianTextView: TextView = itemView.findViewById(R.id.textNoAntrian)
        private val btnRekamMedis: TextView = itemView.findViewById(R.id.btnRekamMedis)

        // Bind data to views
        fun bind(antrian: Antrianmu) {
            poliTextView.text = antrian.poli
            namaTextView.text = antrian.nama
            tanggalTextView.text = antrian.tanggal_daftar
            noAntrianTextView.text = "No Antrian: ${antrian.no_antrian}"

            // Handle Rekam Medis Button Click
            btnRekamMedis.setOnClickListener {
                if (!antrian.rekam_medis.isNullOrEmpty()) {
                    // Get full URL for Rekam Medis using RetrofitClient
                    val rekamMedisUrl = RetrofitClient.getRekamMedisUrl(antrian.rekam_medis)
                    openRekamMedis(rekamMedisUrl)
                } else {
                    // Show message if Rekam Medis is not available
                    Toast.makeText(itemView.context, "Rekam Medis belum tersedia", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Method to open Rekam Medis (URL)
        private fun openRekamMedis(url: String) {
            // Open Rekam Medis (can be PDF or any other file type)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            itemView.context.startActivity(intent)
        }
    }
}
