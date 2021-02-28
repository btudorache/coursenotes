package com.example.coursenotes.courselist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursenotes.database.CourseDao
import kotlinx.coroutines.launch

class CourseListViewModel(dataSource: CourseDao): ViewModel() {
    val database = dataSource

    val courses = database.getAllCourses()

    private val _navigateToCreateCourseLiveData = MutableLiveData<Boolean>()
    val navigateToCreateCourseView: LiveData<Boolean>
        get() = _navigateToCreateCourseLiveData

    fun navigateToCreateCourse() {
        _navigateToCreateCourseLiveData.value = true
    }

    fun doneNavigatingToCreateCourse() {
        _navigateToCreateCourseLiveData.value = false
    }

    private val _navigateToCourseDetail = MutableLiveData<Long?>()
    val navigateToCourseDetail: LiveData<Long?>
        get() = _navigateToCourseDetail

    fun navigateToCourseDetail(courseId: Long) {
        _navigateToCourseDetail.value = courseId
    }

    fun doneNavigatingToCourseDetail() {
        _navigateToCourseDetail.value = null
    }

    fun clearCourses() {
        viewModelScope.launch {
            database.clear()
        }
    }
}