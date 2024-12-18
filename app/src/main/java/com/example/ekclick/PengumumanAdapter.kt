package com.example.ekclick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PengumumanAdapter(private val pengumumanList: List<String>) :
    RecyclerView.Adapter<PengumumanAdapter.PengumumanViewHolder>() {

    // ViewHolder untuk item pengumuman
    inner class PengumumanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPengumuman: TextView = itemView.findViewById(R.id.textPengumuman)
    }

    // Inflate layout untuk item pengumuman
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PengumumanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pengumuman, parent, false)
        return PengumumanViewHolder(view)
    }

    // Bind data ke item view
    override fun onBindViewHolder(holder: PengumumanViewHolder, position: Int) {
        holder.textPengumuman.text = pengumumanList[position]
    }

    // Return jumlah item dalam list
    override fun getItemCount(): Int {
        return pengumumanList.size
    }
}
