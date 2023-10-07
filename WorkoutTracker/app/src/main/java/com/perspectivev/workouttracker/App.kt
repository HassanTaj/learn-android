package com.perspectivev.workouttracker

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.perspectivev.workouttracker.ui.navigation.NavigationHost
import com.perspectivev.workouttracker.ui.navigation.BottomNavItem
import com.perspectivev.workouttracker.ui.navigation.BottomNavigationBar
import com.perspectivev.workouttracker.ui.navigation.Screen

/**
 * Top level composable that represents screens for the application.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInit(navController: NavHostController = rememberNavController()) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(items = listOf(
                BottomNavItem(
                    Screen.Home.name,
                    Screen.Home.route,
                    Icons.Filled.Home,
                    Icons.Outlined.Home
                ),
                BottomNavItem(
                    Screen.Progress.name,
                    Screen.Progress.route,
                    Icons.Filled.AccountCircle,
                    Icons.Outlined.AccountCircle
                ),
                BottomNavItem(
                    Screen.Setting.name,
                    Screen.Setting.route,
                    Icons.Filled.Settings,
                    Icons.Outlined.Settings
                ),
            ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                })
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            NavigationHost(navController = navController)
        }
    }
}


/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back"//stringResource(string.back_button)
                    )
                }
            }
        }
    )
}
