package com.example.proyekakhir.ui.detail

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.storage.StorageManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekakhir.ui.inputAnggaran.InputAnggaranActivity
import com.example.proyekakhir.adapter.DetailAnggaranAdapter
import com.example.proyekakhir.databinding.ActivityDetailBinding
import com.example.proyekakhir.helper.BudgetDetailListener
import com.example.proyekakhir.helper.BudgetFactory
import com.example.proyekakhir.helper.Type
import com.example.proyekakhir.model.Data
import com.example.proyekakhir.ui.homepage.MainActivity
import java.io.File
import java.io.FileOutputStream

class DetailActivity : AppCompatActivity(), BudgetDetailListener {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var dataBudget : ArrayList<Data>
    private var id : Int = 0
    private val WRITE_EXTERNAL_STORAGE_REQUEST = 101
    private lateinit var filePdfOutput : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLayoutManager()
        detailViewModel = ViewModelProvider(this@DetailActivity, BudgetFactory.getInstance(application))[DetailViewModel::class.java]
        dataBudget = ArrayList()

        tryCheckPermission()

        val storageManager = getSystemService(STORAGE_SERVICE) as StorageManager
        val storageVolume = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            storageManager.storageVolumes.get(0)
        } else {
            TODO("VERSION.SDK_INT < N")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filePdfOutput = File(storageVolume.directory?.path.toString() + "/Download/coba.pdf")
        }

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

        binding.printButton.setOnClickListener {
            val pdfDocument = PdfDocument()
            val titlePaint = Paint()
            val pageInfo = PdfDocument.PageInfo.Builder(binding.relativeLayout.width, binding.relativeLayout.height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas

            canvas.save()

            titlePaint.textAlign = Paint.Align.CENTER
            titlePaint.textSize = 70F
            canvas.drawText("Rekap Anggaran Perusahaan", binding.relativeLayout.width/2F, 270F, titlePaint)
            
            canvas.translate(10F, 400F)
            binding.tableAnggaran.draw(page.canvas)

            pdfDocument.finishPage(page)
            pdfDocument.writeTo(FileOutputStream(filePdfOutput, false))

            canvas.restore()

            pdfDocument.close()
        }
    }

    private fun tryCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkWriteExternalStoragePermission()
        }
    }

    private fun checkWriteExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this@DetailActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this@DetailActivity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@DetailActivity, "Izin diberikan", Toast.LENGTH_SHORT).show()
            }
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