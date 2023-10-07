package com.perspectivev.workouttracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.perspectivev.workouttracker.ui.screens.exercise.ExerciseScreen
import com.perspectivev.workouttracker.ui.screens.home.HomeScreen
import com.perspectivev.workouttracker.ui.screens.progress.ProgressScreen
import com.perspectivev.workouttracker.ui.screens.settings.SettingScreen
import com.perspectivev.workouttracker.ui.screens.workout.WorkoutScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost (
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
    navController = navController,
    startDestination = Screen.Home.route,
    modifier = Modifier.padding(15.dp)
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Progress.route) {
            ProgressScreen()
        }
        composable(route = Screen.Setting.route) {
            SettingScreen(modifier,navController)
        }
        composable(route = Screen.Workout.route) {
            WorkoutScreen()
        }
        composable(route = Screen.Exercise.route) {
            ExerciseScreen()
        }
//        composable(route = Screens.Workout.options.route) {
//            ItemEntryScreen(
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
//            )
//        }
    }

}