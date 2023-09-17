package com.perspectivev.workouttracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("description") var description: String?,
    @ColumnInfo("isDone") var isDone: Boolean,
    @ColumnInfo("isDeleted") var isDeleted: Boolean
)
