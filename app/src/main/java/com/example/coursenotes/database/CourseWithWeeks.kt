package com.example.coursenotes.database

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithWeeks(
    @Embedded
    val course: Course,

    @Relation(parentColumn = "id", entityColumn = "course_id")
    val weeks: List<Week>
)
