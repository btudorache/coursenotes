package com.example.coursenotes.weekdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursenotes.database.Week
import com.example.coursenotes.database.WeekDao
import kotlinx.coroutines.launch

class WeekDetailViewModel(val weekId: Long,
                          dataSource: WeekDao) : ViewModel() {

    val database = dataSource

    val week = database.getWeeksWithId(weekId)

    fun updateWeekData(editTextData: String, editTextTitle: String) {
        if (editTextData != week.value?.text || editTextTitle != week.value?.name) {
            viewModelScope.launch {
                val newWeek = week.value?.copy()
                if (newWeek != null) {
                    newWeek.text = editTextData
                    newWeek.name = editTextTitle
                    database.update(newWeek)
                }
            }
        }
    }
}