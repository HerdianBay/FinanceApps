package com.example.proyekakhir.ui.inputKategori

import androidx.lifecycle.ViewModel
import com.example.proyekakhir.repo.BudgetGroupRepository

class InputKategoriViewModel(private val budgetGroupRepo : BudgetGroupRepository) : ViewModel() {

    fun addBudgetGroup(name : String, description : String) = budgetGroupRepo.addBudgetGroup(name, description)

    fun updateBudgetGroup(id : Int, name: String, description: String) = budgetGroupRepo.updateBudgetGroup(id, name, description)
}