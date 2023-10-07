package com.perspectivev.workouttracker.data.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.perspectivev.workouttracker.data.infrastructure.AppDatabase
import com.perspectivev.workouttracker.data.repositories.ExerciseRepository
import com.perspectivev.workouttracker.data.repositories.WorkoutRepository
import com.perspectivev.workouttracker.ui.screens.exercise.ExerciseViewModel
import com.perspectivev.workouttracker.ui.screens.home.HomeViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                WorkoutRepository(
                    AppDatabase.getDbInstance(getApplication().applicationContext).workoutDao()
                )
            )
//            HomeViewModel(getApplication().container.workoutRepository)
        }
        // Initializer for HomeViewModel
        initializer {
            ExerciseViewModel(
                ExerciseRepository(
                    AppDatabase.getDbInstance(getApplication().applicationContext).exerciseDao()
                )
            )
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.getApplication(): Application = (this[APPLICATION_KEY]!!)