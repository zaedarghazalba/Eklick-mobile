package com.example.ekclick

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val context: Context, private val menuItemList: List<String>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    // Membuat ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    // Mengikat data dengan tampilan
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuItemList[position]
        holder.bind(menuItem)
    }

    // Mengembalikan jumlah item
    override fun getItemCount(): Int {
        return menuItemList.size
    }

    // ViewHolder untuk item menu
    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMenu: TextView = itemView.findViewById(R.id.textMenu)

        init {
            itemView.setOnClickListener {
                // Ketika item diklik, kirim data nama poli ke VerifDataActivity
                val intent = Intent(context, VerifDataActivity::class.java)
                intent.putExtra("menuName", menuItemList[adapterPosition]) // Mengirimkan nama menu
                context.startActivity(intent)
            }
        }

        // Fungsi untuk mengikat data ke tampilan
        fun bind(menuItem: String) {
            textMenu.text = menuItem
        }
    }
}


