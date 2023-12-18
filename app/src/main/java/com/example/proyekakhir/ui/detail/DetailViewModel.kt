package com.example.proyekakhir.ui.detail

import androidx.lifecycle.ViewModel
import com.example.proyekakhir.helper.Type
import com.example.proyekakhir.repo.BudgetRepository

class DetailViewModel(private val budgetRepository: BudgetRepository) : ViewModel() {

    fun getBudget() = budgetRepository.getBudget()

    fun newDataBudget() = budgetRepository.newDataBudget

    fun deleteBudget(id : Int) = budgetRepository.deleteBudget(id)
}