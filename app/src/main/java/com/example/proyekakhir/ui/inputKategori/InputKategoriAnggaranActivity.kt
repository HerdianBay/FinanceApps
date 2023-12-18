package com.example.proyekakhir.ui.inputKategori

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.proyekakhir.databinding.ActivityInputKategoriAnggaranBinding
import com.example.proyekakhir.helper.BudgetGroupFactory
import com.example.proyekakhir.model.DataItem
import com.example.proyekakhir.ui.homepage.MainActivity

class InputKategoriAnggaranActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInputKategoriAnggaranBinding
    private lateinit var inputKategoriViewModel: InputKategoriViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputKategoriAnggaranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inputKategoriViewModel = ViewModelProvider(this@InputKategoriAnggaranActivity, BudgetGroupFactory.getInstance(application))[InputKategoriViewModel::class.java]

        val id = intent.getIntExtra(MainActivity.ID, 0)

        if (id == 0) {
            binding.simpanButton.setOnClickListener {
                val name = binding.judulAnggaranEditText.text.toString()
                val description = binding.keteranganEditText.text.toString()
                inputKategoriViewModel.addBudgetGroup(name, description)
                finish()
            }
        } else {
            val data = intent.getParcelableExtra<DataItem>(MainActivity.DATA)
            data?.let {
                binding.apply {
                    judulAnggaranEditText.setText(data.namaKelompok)
                    keteranganEditText.setText(data.deskripsi)
                    simpanButton.setOnClickListener {
                        val name = binding.judulAnggaranEditText.text.toString()
                        val description = binding.keteranganEditText.text.toString()
                        data.id?.let {
                            inputKategoriViewModel.updateBudgetGroup(it, name, description)
                        }
                        finish()
                    }
                }
            }
        }
    }
}