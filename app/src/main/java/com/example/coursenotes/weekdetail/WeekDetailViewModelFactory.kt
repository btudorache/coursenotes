package com.example.coursenotes.weekdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coursenotes.database.WeekDao


class WeekDetailViewModelFactory(private val weekId: Long,
                                 private val dataSource: WeekDao): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeekDetailViewModel::class.java)) {
            return WeekDetailViewModel(weekId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}