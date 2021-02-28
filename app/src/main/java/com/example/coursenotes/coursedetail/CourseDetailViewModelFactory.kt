package com.example.coursenotes.coursedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coursenotes.createcourse.CreateCourseViewModel
import com.example.coursenotes.database.CourseDao
import com.example.coursenotes.database.WeekDao


class CourseDetailViewModelFactory(private val courseId: Long,
                                   private val dataSource: CourseDao): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CourseDetailViewModel::class.java)) {
            return CourseDetailViewModel(courseId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}