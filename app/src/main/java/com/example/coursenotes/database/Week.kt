package com.example.coursenotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "week_table",
    foreignKeys = [
        ForeignKey(entity = Course::class, parentColumns = ["id"], childColumns = ["course_id"], onDelete = ForeignKey.CASCADE)
    ],

)
data class Week(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val weekId: Long = 0L,

    @ColumnInfo(name = "course_id")
    val courseCreatorId: Long = 0L,

    @ColumnInfo(name = "week_name")
    var name: String = "",

    @ColumnInfo(name = "week_text")
    var text: String = ""
)