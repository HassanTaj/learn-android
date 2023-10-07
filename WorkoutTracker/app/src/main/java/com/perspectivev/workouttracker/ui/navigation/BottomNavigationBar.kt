package com.perspectivev.workouttracker.ui.navigation

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.perspectivev.workouttracker.ui.theme.WorkoutTrackerTheme

data class BottomNavItem(
    val name: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int = 0,
    val hasUpdates: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = Modifier,
        containerColor = Color(0xF424242)
    ) {
        items?.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary
                ),
                icon = {
                    if (item.hasUpdates) {
                        BadgedBox(badge = {
                            if (item.badgeCount > 0) {
                                Badge { Text(item.badgeCount.toString()) }
                            } else {
                                Badge { }
                            }
                        }) {
                            Icon(
                                imageVector = if (selected)
                                    item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.name
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (selected)
                                item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.name
                        )
                    }
                },
                label = {
                    Text(
                        text = item.name,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                })
        }

    }
}

@Composable
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
fun BottomNavBarPreview() {
    WorkoutTrackerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
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
                navController = rememberNavController(),
                onItemClick = {})
        }
    }
}

