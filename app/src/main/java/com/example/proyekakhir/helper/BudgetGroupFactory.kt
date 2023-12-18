package com.example.proyekakhir.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyekakhir.injection.Injection
import com.example.proyekakhir.repo.BudgetGroupRepository
import com.example.proyekakhir.ui.homepage.HomeViewModel
import com.example.proyekakhir.ui.inputKategori.InputKategoriViewModel

class BudgetGroupFactory(private val budgetGroupRepo : BudgetGroupRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(budgetGroupRepo) as T
        } else if (modelClass.isAssignableFrom(InputKategoriViewModel::class.java)) {
            return InputKategoriViewModel(budgetGroupRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance : BudgetGroupFactory? = null
        fun getInstance(context : Application) : BudgetGroupFactory =
            instance ?: synchronized(this) {
                instance ?: BudgetGroupFactory(Injection.provideBudgetGroupRepository(context))
            }.also { instance = it }
    }
}