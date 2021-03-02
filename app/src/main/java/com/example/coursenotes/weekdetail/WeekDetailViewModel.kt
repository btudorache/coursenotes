package com.example.coursenotes.weekdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursenotes.database.CourseDao
import com.example.coursenotes.database.WeekDao
import kotlinx.coroutines.launch

class WeekDetailViewModel(
    val weekId: Long,
    courseDataSource: CourseDao,
    weekDataSource: WeekDao
) : ViewModel() {

    private val courseDatabase = courseDataSource
    private val weekDatabase = weekDataSource

    val week = weekDatabase.getWeeksWithId(weekId)

    fun updateWeekData(editTextData: String, editTextTitle: String) {
        if (editTextData != week.value?.text || editTextTitle != week.value?.name) {
            viewModelScope.launch {
                week.value?.let {
                    val newWeek = it.copy(text = editTextData, name = editTextTitle)
                    weekDatabase.update(newWeek)
                }
            }
        }
    }

    private val _navigateToCourseDetail = MutableLiveData<Long?>()
    val navigateToCourseDetail: LiveData<Long?>
        get() = _navigateToCourseDetail

    fun doneNavigatingToCourseDetail() {
        _navigateToCourseDetail.value = null
    }

    fun deleteWeek() {
        viewModelScope.launch {
            week.value?.let {
                val course = courseDatabase.getCourseWithId(it.courseCreatorId)
                val newCourse = course.copy(duration = course.duration - 1)

                weekDatabase.delete(it)
                courseDatabase.update(newCourse)

                _navigateToCourseDetail.value = course.courseId
            }
        }
    }
}
