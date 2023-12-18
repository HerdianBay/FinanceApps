package com.example.proyekakhir.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekakhir.databinding.LayoutDetailAnggaranBinding
import com.example.proyekakhir.helper.BudgetDetailListener
import com.example.proyekakhir.model.Data

class DetailAnggaranAdapter(private val listBudget : List<Data>, private val budgetDetailListener: BudgetDetailListener) : RecyclerView.Adapter<DetailAnggaranAdapter.ViewHolder>() {

    class ViewHolder(var binding : LayoutDetailAnggaranBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutDetailAnggaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listBudget.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val budget = listBudget[position]
        holder.binding.apply {
            dataTanggal.text = budget.tanggal
            dataKet.text = budget.keterangan
            dataType.text = budget.jenis
            dataNominal.text = budget.anggaran.toString()
            budgetRow.setOnClickListener {
                budgetDetailListener.onClickItem(budget)
            }
        }
    }
}