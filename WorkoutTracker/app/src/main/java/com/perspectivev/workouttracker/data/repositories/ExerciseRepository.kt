package com.perspectivev.workouttracker.data.repositories

import com.perspectivev.workouttracker.data.daos.ExerciseDao
import com.perspectivev.workouttracker.data.daos.WorkoutDao
import com.perspectivev.workouttracker.data.entities.Exercise
import com.perspectivev.workouttracker.data.entities.Workout
import com.perspectivev.workouttracker.data.infrastructure.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ExerciseRepository(
    dao: ExerciseDao,
    allItems: Flow<List<Exercise>> = MutableStateFlow(emptyList()),
    searchResults: MutableStateFlow<List<Exercise>> = MutableStateFlow(emptyList())
) : Repository<Exercise, ExerciseDao>(dao, allItems, searchResults) {

    init {
        this.allItems = dao.getAll()
    }
}