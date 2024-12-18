package com.example.ekclick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ekclick.model.Kuota

class KuotaAdapter(private val kuotaList: List<Kuota>) :
    RecyclerView.Adapter<KuotaAdapter.KuotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KuotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kuota, parent, false)
        return KuotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: KuotaViewHolder, position: Int) {
        val kuota = kuotaList[position]
        holder.namaTextView.text = kuota.nama
        holder.noAntrianTextView.text = kuota.no_antrian.toString()
        holder.poliTextView.text = kuota.poli
        holder.tanggalDaftarTextView.text = kuota.tanggal_daftar
    }

    override fun getItemCount() = kuotaList.size

    class KuotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val namaTextView: TextView = view.findViewById(R.id.tvNama)
        val noAntrianTextView: TextView = view.findViewById(R.id.tvNoAntrian)
        val poliTextView: TextView = view.findViewById(R.id.tvPoli)
        val tanggalDaftarTextView: TextView = view.findViewById(R.id.tvTanggal)
    }

    fun updateData(newData: List<Kuota>) {
        (kuotaList as MutableList).clear()
        kuotaList.addAll(newData)
        notifyDataSetChanged()
    }
}
