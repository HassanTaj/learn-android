package com.perspectivev.workouttracker.data.infrastructure

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.perspectivev.workouttracker.data.daos.ExerciseDao
import com.perspectivev.workouttracker.data.daos.WorkoutDao
import com.perspectivev.workouttracker.data.entities.Exercise
import com.perspectivev.workouttracker.data.entities.Workout

@Database(
    entities = [
        Workout::class,
        Exercise::class
    ], version = 4, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDbInstance(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo-app-db"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
