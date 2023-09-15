package com.perspectivev.todo.db.entities

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perspectivev.todo.db.daos.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodoViewModel(private val repository:TodoRepository) : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos = _todos.asStateFlow()


    private val screenData = repository.get()
    private val _screenDataFlow = MutableStateFlow(screenData)
    internal val screenDataFlow = _screenDataFlow.asStateFlow()

    internal fun updateIsDone(index:Int,done:Boolean) {
        viewModelScope.launch {
            screenData[index].isDone = done;

            _screenDataFlow.emit(screenData)
        }
    }


    private val _uiTodos = MutableStateFlow(SnapshotStateList<Todo>())
    val todosState: StateFlow<SnapshotStateList<Todo>> = _uiTodos

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(LatestUiState.Success(emptyList()))
    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<LatestUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.allItems
                // Update View with the latest favorite news
                // Writes to the value property of MutableStateFlow,
                // adding a new element to the flow and updating all
                // of its collectors
                .collect { todoList ->
                    _uiState.value = LatestUiState.Success(todoList)
                    _uiTodos.value.addAll(todoList) //= mutableStateListOf<Todo>(todoList)
                }
        }
    }
}
// Represents different states for the LatestNews screen
sealed class LatestUiState {
    data class Success(val todos: List<Todo>): LatestUiState()
    data class Error(val exception: Throwable): LatestUiState()
}