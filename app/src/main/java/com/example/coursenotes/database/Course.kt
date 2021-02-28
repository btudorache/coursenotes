package com.example.coursenotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
data class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val courseId: Long = 0L,

    @ColumnInfo(name = "course_name")
    var courseName: String = "",

    @ColumnInfo(name = "course_duration")
    var duration: Int = -1,

    @ColumnInfo(name = "course_color")
    var color: Int = -1
)