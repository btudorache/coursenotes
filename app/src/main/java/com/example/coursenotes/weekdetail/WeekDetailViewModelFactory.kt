package com.example.coursenotes.weekdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coursenotes.database.CourseDao
import com.example.coursenotes.database.WeekDao

class WeekDetailViewModelFactory(
    private val weekId: Long,
    private val courseDataSource: CourseDao,
    private val weekDataSource: WeekDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeekDetailViewModel::class.java)) {
            return WeekDetailViewModel(weekId, courseDataSource, weekDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
