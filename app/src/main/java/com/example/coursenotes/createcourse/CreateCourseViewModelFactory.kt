package com.example.coursenotes.createcourse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coursenotes.database.CourseDao
import com.example.coursenotes.database.WeekDao

class CreateCourseViewModelFactory(
    private val courseDataSource: CourseDao,
    private val weekDataSource: WeekDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCourseViewModel::class.java)) {
            return CreateCourseViewModel(courseDataSource, weekDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
