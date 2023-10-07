package com.perspectivev.workouttracker.ui.navigation

class ScreenOptions(val route:String,val name:String){
}
sealed class Screen(val options:ScreenOptions) {
    val route = options.route
    val name = options.name

    object Home:Screen(ScreenOptions("home_screen","Home"))
    object Progress:Screen(ScreenOptions("progress_screen","Progress"))
    object Setting:Screen(ScreenOptions("setting_screen", "Settings"))



    object Workout:Screen(ScreenOptions("workout_screen", "Workout")) {
        object Save:Screen(ScreenOptions("workout_save","Save Workout"))
    }
    object Exercise:Screen(ScreenOptions("exercise_screen", "Exercise")){
        object Save:Screen(ScreenOptions("exercise_save","Save Exercise"))
    }
}
