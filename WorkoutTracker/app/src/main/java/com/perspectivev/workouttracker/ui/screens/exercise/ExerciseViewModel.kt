package com.perspectivev.workouttracker.ui.screens.exercise

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perspectivev.workouttracker.data.entities.Exercise
import com.perspectivev.workouttracker.data.repositories.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {
    val exerciseUiState: StateFlow<ExerciseUiState> =
        repository.getAll().map { ExerciseUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExerciseUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val _items = MutableStateFlow<SnapshotStateList<Exercise>>(SnapshotStateList())
    val items = _items.asStateFlow()

    internal fun getList(): StateFlow<SnapshotStateList<Exercise>> {
        return _items.asStateFlow()
    }

    internal fun insert(model: Exercise) {
        viewModelScope.launch {
            repository.insert(model)
            _items.emit(items.value)
        }
    }

    internal fun update(model: Exercise, listToUpdate: List<Exercise>) {
        viewModelScope.launch {
            repository.update(model)
            _items.value = listToUpdate.toMutableStateList()
        }
    }

    internal fun delete(model: Exercise) {
        viewModelScope.launch {
            repository.delete(model)
            _items.emit(items.value)
        }
    }

    private fun initializeList() {
        viewModelScope.launch {
            repository.allItems
                .collect { todoList ->
                    _items.value = todoList.toMutableStateList()
                }
        }
    }


    init {
        initializeList()
    }

}

/**
 * Ui State for HomeScreen
 */
data class ExerciseUiState(val itemList: List<Exercise> = listOf())