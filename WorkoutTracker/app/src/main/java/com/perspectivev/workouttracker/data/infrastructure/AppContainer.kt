package com.perspectivev.workouttracker.data.infrastructure

import android.content.Context
import com.perspectivev.workouttracker.data.repositories.ExerciseRepository
import com.perspectivev.workouttracker.data.repositories.WorkoutRepository

interface IAppContainer {
    val workoutRepository: WorkoutRepository
    val exerciseRepository: ExerciseRepository
}
class AppContainer(private val context: Context) : IAppContainer {
    override val workoutRepository: WorkoutRepository by lazy {
        WorkoutRepository(AppDatabase.getDbInstance(context).workoutDao())
    }
    override val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepository(AppDatabase.getDbInstance(context).exerciseDao())
    }
}
