package com.perspectivev.workouttracker.data.repositories

import com.perspectivev.workouttracker.data.daos.WorkoutDao
import com.perspectivev.workouttracker.data.entities.Workout
import com.perspectivev.workouttracker.data.infrastructure.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WorkoutRepository(
    dao: WorkoutDao,
    allItems: Flow<List<Workout>> = MutableStateFlow(emptyList()),
    searchResults: MutableStateFlow<List<Workout>> = MutableStateFlow(emptyList())
) : Repository<Workout, WorkoutDao>(dao, allItems, searchResults) {

    init {
        this.allItems = dao.getAll()
    }
}