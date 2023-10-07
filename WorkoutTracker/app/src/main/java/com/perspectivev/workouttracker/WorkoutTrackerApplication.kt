package com.perspectivev.workouttracker

import android.app.Application
import com.perspectivev.workouttracker.data.infrastructure.AppContainer
import com.perspectivev.workouttracker.data.infrastructure.IAppContainer

class WorkoutTrackerApplication : Application() {
    lateinit var container: IAppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
