package com.perspectivev.todo.db.daos

import com.perspectivev.todo.db.entities.Todo
import com.perspectivev.todo.db.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TodoRepository(
    dao: TodoDao,
    allItems: Flow<List<Todo>> = MutableStateFlow(emptyList()),
    searchResults: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
) : Repository<Todo, TodoDao>(dao, allItems, searchResults) {

    init {
        this.allItems = dao.getAll()
    }
}