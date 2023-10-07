package com.perspectivev.workouttracker.ui.screens.exercise

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.perspectivev.workouttracker.data.entities.Exercise
import com.perspectivev.workouttracker.data.viewmodels.AppViewModelProvider
import com.perspectivev.workouttracker.ui.theme.WorkoutTrackerTheme
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@ExperimentalMaterial3Api
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    viewModel: ExerciseViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current;

    val exerciseList by viewModel.items.collectAsState(emptyList())
    var model by remember {
        val td = Exercise(
            0,
            ""
        )
        mutableStateOf(td)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                    model = Exercise(
                        0,
                        ""
                    )
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        ExerciseList(
            exercises = exerciseList,
            onEdit = {
                model = it
                showBottomSheet = true
            },
            onDelete = {
                viewModel.delete(it);
            })

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                modifier = Modifier
                    .padding(15.dp)
                    .height(220.dp)
            ) {
                // Sheet content
                Column(
                    Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                ) {
                    var name by rememberSaveable { mutableStateOf(if(model.id==0) "" else model.name) }
                    var nameHasError by remember { mutableStateOf(false) }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = name,
                            onValueChange = {
                                name = it
                                nameHasError = (name == null || name.trim() == "")
                            },
                            label = { Text("Name") },
                            isError = nameHasError
                        )
                    }
                    Button(
                        onClick = {
                            nameHasError = (name == null || name.trim() == "")
                            if (!nameHasError) {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                                if (model.id == 0) {
                                    viewModel.insert(Exercise(0, name))
                                }else {
                                    model.name = name
                                    viewModel.update(model,exerciseList);
                                    model = Exercise(0, "")
                                }
                            } else {
                                Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT)
                                    .show();
                            }
                        },
                        modifier = Modifier
                            .padding(0.dp, 10.dp)
                            .align(Alignment.End)
                    ) {
                        Text("Save")
                    }
                }
            }
        }

    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@ExperimentalMaterial3Api
fun ExerciseList(
    exercises: List<Exercise>,
    onEdit: (exercise: Exercise) -> Unit,
    onDelete: (exercise: Exercise) -> Unit
) {
    Column {
        Row(modifier = Modifier.padding(15.dp,15.dp)) {
            Text(
                text = "Exercise",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()
        LazyColumn(content = {
            itemsIndexed(items = exercises, key = { _, it -> it.id }) { index, it ->
                ListItem(
                    modifier = Modifier.clickable { onEdit(it) },
                    headlineContent = { Text(it.name) },
                    trailingContent = {
                        IconButton(onClick = { onDelete(it) }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "delete",
                                tint = Color(0xFFEA7171)
                            )
                        }
                    }
                )
            }
        })
    }
}


@Composable
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
//@Preview(name = "Full", showSystemUi = true)
@ExperimentalMaterial3Api
fun ExerciseScreenPreview() {
    WorkoutTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ExerciseList(
                listOf(
                    Exercise(1, "Jumping Jacks"),
                    Exercise(2, "Pushups"),
                ),
                onEdit = {},
                onDelete = {}
            )
        }
    }
}