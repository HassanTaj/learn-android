package com.perspectivev.todo.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("todos")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("task") var task: String,
    @ColumnInfo("description") var description: String?,
    @ColumnInfo("isDone") var isDone: Boolean,
    @ColumnInfo("isDeleted") var isDeleted: Boolean,
)
