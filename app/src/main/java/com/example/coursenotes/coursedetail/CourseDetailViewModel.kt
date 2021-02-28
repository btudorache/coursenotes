package com.example.coursenotes.coursedetail

import androidx.lifecycle.*
import com.example.coursenotes.database.*
import kotlinx.coroutines.launch

class CourseDetailViewModel(val courseId: Long,
                            dataSource: CourseDao) : ViewModel() {
    val database = dataSource

    val courseAndWeeks = database.getCourseAndWeeksWithId(courseId)

    private val _navigateToWeekDetail = MutableLiveData<Long?>()
    val navigateToWeekDetail: LiveData<Long?>
        get() = _navigateToWeekDetail

    fun navigateToWeekDetail(weekId: Long) {
        _navigateToWeekDetail.value = weekId
    }

    fun doneNavigatingToWeekDetail() {
        _navigateToWeekDetail.value = null
    }
}