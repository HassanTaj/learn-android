package com.perspectivev.workouttracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("exercises")
data class Exercise (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("name") var name: String,
)