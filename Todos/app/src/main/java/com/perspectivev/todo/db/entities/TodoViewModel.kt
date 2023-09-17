package com.perspectivev.todo.db.entities

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perspectivev.todo.db.daos.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _todos = MutableStateFlow<SnapshotStateList<Todo>>(SnapshotStateList())
    val todos = _todos.asStateFlow()

    internal fun getTodosList(isInspectionMode: Boolean): StateFlow<SnapshotStateList<Todo>> {
        return if (isInspectionMode) {
            val snapShotList = mutableStateListOf<Todo>()
            for (i in 1..8) snapShotList.add(
                Todo(
                    i,
                    "Demo Task $i",
                    null,
                    isDone = false,
                    isDeleted = false
                )
            )
            MutableStateFlow(snapShotList).asStateFlow()
        } else {
            _todos.asStateFlow()
        }
    }

    internal fun insert(todo: Todo) {
        viewModelScope.launch {
            repository.insert(todo)
            _todos.emit(todos.value)
        }
    }

    internal fun update(todo: Todo, listToUpdate: List<Todo>) {
        viewModelScope.launch {
            repository.update(todo)
            _todos.value = listToUpdate.toMutableStateList()
//            getList()
        }
    }

    internal fun delete(todo: Todo) {
        viewModelScope.launch {
            repository.delete(todo)
            _todos.emit(todos.value)
        }
    }

    private fun initializeList() {
        viewModelScope.launch {
            repository.allItems
                // Update View with the latest favorite news
                // Writes to the value property of MutableStateFlow,
                // adding a new element to the flow and updating all
                // of its collectors
                .collect { todoList ->
                    _todos.value = todoList.toMutableStateList()
                }
        }
    }


    init {
        initializeList()
    }
}