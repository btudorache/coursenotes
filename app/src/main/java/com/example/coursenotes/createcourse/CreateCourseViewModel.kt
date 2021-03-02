package com.example.coursenotes.createcourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursenotes.database.Course
import com.example.coursenotes.database.CourseDao
import com.example.coursenotes.database.Week
import com.example.coursenotes.database.WeekDao
import kotlinx.coroutines.launch
import kotlin.random.Random

class CreateCourseViewModel(courseDataSource: CourseDao, weekDataSource: WeekDao) : ViewModel() {
    private val courseDatabase = courseDataSource
    private val weekDatabase = weekDataSource

    private val _navigateToCourseList = MutableLiveData<Boolean>()
    val navigateToCourseList: LiveData<Boolean>
        get() = _navigateToCourseList

    private val _showCreationWarning = MutableLiveData<Boolean>()
    val showCreationWarning: LiveData<Boolean>
        get() = _showCreationWarning

    fun onCreateCourse(courseName: String, duration: String) {
        if (courseName == "" || duration == "") {
            _showCreationWarning.value = true
        } else {
            viewModelScope.launch {
                val course = Course(
                    courseName = courseName,
                    duration = Integer.parseInt(duration),
                    color = Random.nextInt(5)
                )

                val newCourseId = courseDatabase.insert(course)

                val weekList = mutableListOf<Week>()
                for (i in 1..course.duration) {
                    weekList.add(Week(courseCreatorId = newCourseId, name = "Week $i"))
                }

                weekDatabase.insertAll(weekList)

                // set state to navigate back to course list
                _navigateToCourseList.value = true
            }
        }
    }

    fun doneShowingWarning() {
        _showCreationWarning.value = false
    }

    fun doneNavigatingCourseList() {
        _navigateToCourseList.value = false
    }
}
