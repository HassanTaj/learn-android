package com.perspectivev.workouttracker.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.perspectivev.workouttracker.data.infrastructure.IDao
import com.perspectivev.workouttracker.data.entities.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao : IDao<Workout> {
    @Query("select * from workouts")
    override fun get(): List<Workout>

    @Query("SELECT * from workouts WHERE id = :id")
    override fun get(id: Int): Flow<Workout?>

    @Query("SELECT * from workouts ORDER BY id DESC")
    override fun getAll(): Flow<List<Workout>>
}