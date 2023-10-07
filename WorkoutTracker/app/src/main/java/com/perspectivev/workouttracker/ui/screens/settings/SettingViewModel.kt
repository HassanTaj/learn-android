package com.perspectivev.workouttracker.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perspectivev.workouttracker.data.entities.Workout
import com.perspectivev.workouttracker.data.repositories.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

//class SettingViewModel(repository: WorkoutRepository) : ViewModel() {
//    val homeUiState: StateFlow<HomeUiState> =
//        repository.getAll().map { HomeUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = HomeUiState()
//            )
//
//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
//
//
//}

/**
 * Ui State for HomeScreen
 */
//data class HomeUiState(val itemList: List<Workout> = listOf())