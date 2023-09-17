package com.perspectivev.workouttracker.data.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perspectivev.workouttracker.data.repositories.WorkoutRepository
import com.perspectivev.workouttracker.data.entities.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {
    private val _items = MutableStateFlow<SnapshotStateList<Workout>>(SnapshotStateList())
    val items = _items.asStateFlow()

    internal fun getList(isInspectionMode: Boolean): StateFlow<SnapshotStateList<Workout>> {
        return if (isInspectionMode) {
            val snapShotList = mutableStateListOf<Workout>()
            for (i in 1..8) snapShotList.add(
                Workout(
                    i,
                    "Demo Task $i",
                    null,
                    isDone = false,
                    isDeleted = false
                )
            )
            MutableStateFlow(snapShotList).asStateFlow()
        } else {
            _items.asStateFlow()
        }
    }

    internal fun insert(todo: Workout) {
        viewModelScope.launch {
            repository.insert(todo)
            _items.emit(items.value)
        }
    }

    internal fun update(todo: Workout, listToUpdate: List<Workout>) {
        viewModelScope.launch {
            repository.update(todo)
            _items.value = listToUpdate.toMutableStateList()
//            getList()
        }
    }

    internal fun delete(todo: Workout) {
        viewModelScope.launch {
            repository.delete(todo)
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