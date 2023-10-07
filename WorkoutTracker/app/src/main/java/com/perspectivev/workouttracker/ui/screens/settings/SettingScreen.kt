package com.perspectivev.workouttracker.ui.screens.settings

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.perspectivev.workouttracker.ui.navigation.Screen
import com.perspectivev.workouttracker.ui.theme.WorkoutTrackerTheme

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current;
    Column {
        Row(modifier = modifier.padding(5.dp, 5.dp)) {
            Text(
                text = "Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            )
        }
        val settings = listOf<Screen>(
            Screen.Exercise,
            Screen.Workout
        )
        settings.forEach { screenItem ->
            SettingItem(
                modifier,
                screenItem,
                onClick = { screen ->
                    navController.navigate(screen.route)
                })

        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingItem(
    modifier: Modifier = Modifier, screen: Screen, onClick: (Screen) -> Unit
) {
    Button(
        onClick = { onClick(screen) },
        modifier = modifier
            .padding(5.dp, 2.dp, 5.dp, 0.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = screen.name, textAlign = TextAlign.Start
        )
    }
}


@Composable
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
fun SettingItemPreview() {
    WorkoutTrackerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SettingItem(modifier = Modifier, Screen.Home, {})
        }
    }
}

@Composable
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
//@Preview(name = "Full Preview", showSystemUi = true)
fun SettingPreview() {
    WorkoutTrackerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SettingScreen(modifier = Modifier)
        }
    }
}