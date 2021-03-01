package com.example.coursenotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeekDao {
    @Insert
    suspend fun insert(week: Week)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weekList: List<Week>)

    @Update
    suspend fun update(week: Week)

    @Query("SELECT * FROM week_table WHERE id = :key")
    fun getWeeksWithId(key: Long): LiveData<Week>
}