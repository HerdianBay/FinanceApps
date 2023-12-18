package com.example.proyekakhir.helper

import com.example.proyekakhir.model.DataItem

interface BudgetListener {
    fun onClickDelete(dataItem: DataItem)
    fun onClickUpdate(dataItem: DataItem)
}