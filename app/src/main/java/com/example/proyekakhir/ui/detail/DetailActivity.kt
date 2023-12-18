package com.example.proyekakhir.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekakhir.ui.inputAnggaran.InputAnggaranActivity
import com.example.proyekakhir.R
import com.example.proyekakhir.adapter.DetailAnggaranAdapter
import com.example.proyekakhir.databinding.ActivityDetailBinding
import com.example.proyekakhir.helper.BudgetDetailListener
import com.example.proyekakhir.helper.BudgetFactory
import com.example.proyekakhir.helper.Type
import com.example.proyekakhir.model.Data
import com.example.proyekakhir.ui.homepage.MainActivity

class DetailActivity : AppCompatActivity(), BudgetDetailListener {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var dataBudget : ArrayList<Data>
    private var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLayoutManager()
        detailViewModel = ViewModelProvider(this@DetailActivity, BudgetFactory.getInstance(application))[DetailViewModel::class.java]
        dataBudget = ArrayList()

        id = intent.getIntExtra(MainActivity.ID, 0)

        binding.budgetData.recycledViewPool.clear()

        fetchData()

        detailViewModel.newDataBudget().observe(this@DetailActivity, {detailDataBudget ->
            if (detailDataBudget != null) {
                val newData = dataBudget.find { it.id ==  detailDataBudget.id}
                if (newData != null) {
                    newData.tanggal = detailDataBudget.tanggal
                    newData.anggaran = detailDataBudget.anggaran
                    newData.jenis = detailDataBudget.jenis
                    newData.keterangan = detailDataBudget.keterangan
                    submitData(dataBudget)
                } else {
                    dataBudget.add(detailDataBudget)
                    submitData(dataBudget)
                }
            }
        })

        binding.addDataButton.setOnClickListener {
            val intent = Intent(this@DetailActivity, InputAnggaranActivity::class.java)
            intent.putExtra(MainActivity.ID, id)
            startActivity(intent)
        }
    }

    private fun setLayoutManager() {
        val layoutManager = LinearLayoutManager(this@DetailActivity)
        binding.budgetData.layoutManager = layoutManager
    }

    private fun submitData(data : List<Data>) {
        var nominalIncome = 0
        var nominalOutcome = 0
        val adapter = DetailAnggaranAdapter(data, this)
        binding.budgetData.adapter = adapter
        for (typeNominal in data) {
            if (typeNominal.jenis == Type.masuk.toString()) {
                nominalIncome += typeNominal.anggaran!!
            } else {
                nominalOutcome += typeNominal.anggaran!!
            }
        }
        binding.danaMasuk.text = nominalIncome.toString()
        binding.danaKeluar.text = nominalOutcome.toString()
        binding.danaSisa.text = (nominalIncome - nominalOutcome).toString()
    }

    override fun onClickItem(data: Data) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah Yang Ingin Anda Lakukan?")
            .setPositiveButton("Update", { dialog, _ ->
                val intent = Intent(this@DetailActivity, InputAnggaranActivity::class.java)
                intent.putExtra(MainActivity.ID, data.id)
                intent.putExtra(MainActivity.DATA, data)
                startActivity(intent)
            })
            .setNegativeButton("Delete", { dialog, _ ->
                detailViewModel.deleteBudget(data.id!!)
                fetchData()
            })
            .show()
    }

    private fun fetchData() {
        detailViewModel.getBudget().observe(this@DetailActivity, {
            dataBudget.clear()
            for (dataDetailBudget in it) {
                if (dataDetailBudget.idKelompokAnggaran == id) {
                    dataBudget.add(dataDetailBudget)
                }
            }
            submitData(dataBudget)
        })
    }
}