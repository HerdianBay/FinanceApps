package com.example.proyekakhir.ui.inputAnggaran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.proyekakhir.R
import com.example.proyekakhir.databinding.ActivityInputAnggaranBinding
import com.example.proyekakhir.helper.BudgetFactory
import com.example.proyekakhir.helper.Type
import com.example.proyekakhir.model.Data
import com.example.proyekakhir.ui.homepage.MainActivity
import java.text.SimpleDateFormat
import java.util.Locale

class InputAnggaranActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInputAnggaranBinding
    private lateinit var radioValue : Type
    private lateinit var inputAnggaranViewModel: InputAnggaranViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputAnggaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputAnggaranViewModel = ViewModelProvider(this@InputAnggaranActivity, BudgetFactory.getInstance(application))[InputAnggaranViewModel::class.java]
        val id = intent.getIntExtra(MainActivity.ID, 0)
        val data = intent.getParcelableExtra<Data>(MainActivity.DATA)

        binding.jenisRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            radioValue = if (checkedId == R.id.pemasukkanRadioButton) {
                Type.masuk
            } else {
                Type.keluar
            }
        }

        if (data == null) {
            binding.simpanButton.setOnClickListener {
                binding.apply {
                    val dateRaw = tanggalEditText.text.toString()
                    val description = keteranganEditText.text.toString()
                    val type = radioValue
                    val budget = nominalEditText.text.toString().toInt()
                    inputAnggaranViewModel.addBudget(id, budget, dateRaw, type, description)
                    finish()
                }
            }
        } else {
            binding.apply {
                tanggalEditText.setText(data.tanggal)
                keteranganEditText.setText(data.keterangan)
                nominalEditText.setText(data.anggaran.toString())
                if (data.jenis == Type.masuk.toString()) {
                    pemasukkanRadioButton.isChecked = true
                    pengeluaranRadioButton.isChecked = false
                } else {
                    pengeluaranRadioButton.isChecked = true
                    pemasukkanRadioButton.isChecked = false
                }
                binding.simpanButton.setOnClickListener {
                    val dateRaw = tanggalEditText.text.toString()
                    val description = keteranganEditText.text.toString()
                    val type = radioValue
                    val budget = nominalEditText.text.toString().toInt()
                    inputAnggaranViewModel.updateDataBudget(data.id!!, data.idKelompokAnggaran!!, budget, dateRaw, type, description)
                    finish()
                }
            }
        }
    }
}