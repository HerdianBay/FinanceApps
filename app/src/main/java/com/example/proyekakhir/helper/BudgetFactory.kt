package com.example.proyekakhir.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyekakhir.injection.Injection
import com.example.proyekakhir.repo.BudgetRepository
import com.example.proyekakhir.ui.detail.DetailViewModel
import com.example.proyekakhir.ui.inputAnggaran.InputAnggaranViewModel

class BudgetFactory(private val budgetRepository: BudgetRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputAnggaranViewModel::class.java)) {
            return InputAnggaranViewModel(budgetRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(budgetRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance : BudgetFactory? = null
        fun getInstance(context : Application) : BudgetFactory =
            instance ?: synchronized(this) {
                instance ?: BudgetFactory(Injection.provideBudgetRepository(context))
            }.also { instance = it }
    }
}