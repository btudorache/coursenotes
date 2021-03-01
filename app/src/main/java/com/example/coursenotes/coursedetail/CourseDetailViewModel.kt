package com.example.coursenotes.coursedetail

import androidx.lifecycle.*
import com.example.coursenotes.database.*
import kotlinx.coroutines.launch

class CourseDetailViewModel(val courseId: Long,
                            courseDataSource: CourseDao,
                            weekDataSource: WeekDao) : ViewModel() {
    private val courseDatabase = courseDataSource
    private val weekDatabase = weekDataSource

    val courseAndWeeks = courseDatabase.getCourseAndWeeksWithId(courseId)

    private val _navigateToWeekDetail = MutableLiveData<Long?>()
    val navigateToWeekDetail: LiveData<Long?>
        get() = _navigateToWeekDetail

    fun navigateToWeekDetail(weekId: Long) {
        _navigateToWeekDetail.value = weekId
    }

    fun doneNavigatingToWeekDetail() {
        _navigateToWeekDetail.value = null
    }

    private val _navigateToCourseList = MutableLiveData<Boolean>()
    val navigateToCourseList: LiveData<Boolean>
        get() = _navigateToCourseList


    fun doneNavigatingToCourseList() {
        _navigateToCourseList.value = false
    }

    fun deleteCourse() {
        viewModelScope.launch {
            courseAndWeeks.value?.let {
                courseDatabase.delete(it.course)
            }

            _navigateToCourseList.value = true
        }
    }

    fun createWeek() {
        viewModelScope.launch {
            courseAndWeeks.value?.let {
                val course = it.course.copy(duration = it.course.duration + 1)
                val week = Week(courseCreatorId = it.course.courseId, name = "Week " + course.duration)

                courseDatabase.update(course)
                weekDatabase.insert(week)

                _showWeekCreatedSnackbar.value = true
            }
        }
    }

    private val _showWeekCreatedSnackbar = MutableLiveData<Boolean>()
    val showWeekCreatedSnackbar: LiveData<Boolean>
        get() = _showWeekCreatedSnackbar

    fun doneShowingWeekCreatedSnackbar() {
        _showWeekCreatedSnackbar.value = false
    }
}