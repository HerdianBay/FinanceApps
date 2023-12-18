package com.example.proyekakhir.ui.homepage

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekakhir.adapter.AnggaranAdapter
import com.example.proyekakhir.databinding.ActivityMainBinding
import com.example.proyekakhir.helper.BudgetGroupFactory
import com.example.proyekakhir.helper.BudgetListener
import com.example.proyekakhir.model.DataItem
import com.example.proyekakhir.ui.inputKategori.InputKategoriAnggaranActivity

class MainActivity : AppCompatActivity(), BudgetListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dataList : ArrayList<DataItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLayoutManager()
        homeViewModel = ViewModelProvider(this@MainActivity, BudgetGroupFactory.getInstance(application))[HomeViewModel::class.java]
        dataList = ArrayList()

        fetchData()

        homeViewModel.getNewDataBudgetGroup().observe(this@MainActivity, {dataBudget ->
            if (dataBudget != null) {
                val budget = dataList.find { it.id == dataBudget.id }
                if (budget != null) {
                    budget.namaKelompok = dataBudget.namaKelompok
                    budget.updatedAt = dataBudget.updatedAt
                    budget.deskripsi = dataBudget.deskripsi
                    submitData(dataList)
                } else {
                    dataList.add(dataBudget)
                    submitData(dataList)
                }
            }
        })

        binding.searchAnggaran.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //find data maybe in database or use temporary data, still waiting for model response
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, InputKategoriAnggaranActivity::class.java)
            intent.putExtra(ID, 0)
            startActivity(intent)
        }
    }

    private fun setLayoutManager() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvAnggaran.layoutManager = layoutManager
    }

    private fun submitData(listData : List<DataItem>) {
        val adapter = AnggaranAdapter(listData, this)
        binding.rvAnggaran.adapter = adapter
    }

    private fun fetchData() {
        homeViewModel.getBudgetGroup().observe(this@MainActivity, {dataBudgetGroup ->
            if (dataBudgetGroup != null) {
                dataList.clear()
                dataList.addAll(dataBudgetGroup)
                submitData(dataList)
            } else {
                Toast.makeText(this, "Data Masih Kosong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClickDelete(dataItem: DataItem) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah Anda Ingin Menghapus Data Ini?")
            .setPositiveButton("Yes", { dialog, _ ->
                homeViewModel.deleteBudgetGroup(dataItem.id!!)
                fetchData()
            })
            .setNegativeButton("No", { dialog, _ ->
                dialog.cancel()
            })
            .show()
    }

    override fun onClickUpdate(dataItem: DataItem) {
        val intent = Intent(this@MainActivity, InputKategoriAnggaranActivity::class.java)
        intent.putExtra(ID, dataItem.id)
        intent.putExtra(DATA, dataItem)
        startActivity(intent)
    }

    companion object {
        const val ID = "ID"
        const val DATA = "DATA"
    }
}