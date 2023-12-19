package com.example.proyekakhir.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekakhir.databinding.LayoutItemAnggaranBinding
import com.example.proyekakhir.helper.BudgetListener
import com.example.proyekakhir.model.DataItem
import com.example.proyekakhir.ui.detail.DetailActivity
import com.example.proyekakhir.ui.homepage.MainActivity

class AnggaranAdapter(private val listData : List<DataItem>, private val budgetListener: BudgetListener) : RecyclerView.Adapter<AnggaranAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemAnggaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.binding.apply {
            //binding into view and use click listener to go to detail activity
            tvNamaAnggaran.text = data.namaKelompok
            description.text = data.deskripsi
        }
        holder.binding.apply {
            updateButton.setOnClickListener {
                budgetListener.onClickUpdate(data)
            }
            deleteButton.setOnClickListener {
                budgetListener.onClickDelete(data)
            }
            cardItem.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra(MainActivity.ID, data.id)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = listData.size

    class ViewHolder(var binding : LayoutItemAnggaranBinding) : RecyclerView.ViewHolder(binding.root)
}