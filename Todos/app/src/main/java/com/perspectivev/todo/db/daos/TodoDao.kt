package com.perspectivev.todo.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.perspectivev.todo.db.entities.Todo
import com.perspectivev.todo.db.repository.IDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface TodoDao : IDao<Todo> {
    @Query("select * from todos")
    override fun get(): List<Todo>

    @Query("SELECT * from todos WHERE id = :id")
    override fun get(id: Int): Flow<Todo?>

    @Query("SELECT * from todos ORDER BY id DESC")
    override fun getAll(): Flow<List<Todo>>

}