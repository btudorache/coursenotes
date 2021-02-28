package com.example.coursenotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDao {
    @Insert
    suspend fun insert(course: Course): Long

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Query("DELETE FROM course_table")
    suspend fun clear()

    @Query("SELECT * FROM course_table ORDER BY id DESC")
    fun getAllCourses(): LiveData<List<Course>>

    @Transaction
    @Query("SELECT * FROM course_table WHERE id = :key")
    fun getCourseAndWeeksWithId(key: Long): LiveData<CourseWithWeeks>
}