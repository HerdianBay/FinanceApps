package com.example.proyekakhir.ui.inputAnggaran

import androidx.lifecycle.ViewModel
import com.example.proyekakhir.helper.Type
import com.example.proyekakhir.repo.BudgetRepository
import java.util.Date

class InputAnggaranViewModel(private val budgetRepository: BudgetRepository) : ViewModel() {

    fun addBudget(id : Int, budget : Int, date: String, type: Type, description : String) = budgetRepository.addBudget(id, budget, date, type, description)

    fun updateDataBudget(id: Int, groupId : Int, budget: Int, date: String, type: Type, description: String) = budgetRepository.updateBudget(id, groupId, budget, date, type, description)
}