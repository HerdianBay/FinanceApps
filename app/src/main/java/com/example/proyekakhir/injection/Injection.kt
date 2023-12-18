package com.example.proyekakhir.injection

import android.app.Application
import com.example.proyekakhir.repo.BudgetGroupRepository
import com.example.proyekakhir.repo.BudgetRepository

object Injection {
    fun provideBudgetGroupRepository(context: Application) : BudgetGroupRepository {
        return BudgetGroupRepository.getInstance(context)
    }
    fun provideBudgetRepository(context: Application) : BudgetRepository {
        return BudgetRepository.getInstance(context)
    }
}