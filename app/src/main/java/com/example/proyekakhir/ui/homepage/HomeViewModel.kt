package com.example.proyekakhir.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.proyekakhir.model.DataItem
import com.example.proyekakhir.repo.BudgetGroupRepository

class HomeViewModel(private val budgetGroupRepo : BudgetGroupRepository) : ViewModel() {

    fun getBudgetGroup() : LiveData<List<DataItem>> = budgetGroupRepo.getBudgetGroup()

    fun getNewDataBudgetGroup() : LiveData<DataItem> = budgetGroupRepo.newDataBudgetGroup

    fun deleteBudgetGroup(id : Int) = budgetGroupRepo.deleteBudgetGroup(id)
}