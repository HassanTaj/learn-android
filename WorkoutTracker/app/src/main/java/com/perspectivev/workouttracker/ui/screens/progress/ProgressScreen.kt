package com.perspectivev.workouttracker.ui.screens.progress

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier,
//    viewModel: ProgressViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

//    val homeUiState by viewModel.exerciseUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Text(text = "Progress Screen")
}