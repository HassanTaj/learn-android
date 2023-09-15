package com.perspectivev.todo

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.perspectivev.todo.db.AppDatabase
import com.perspectivev.todo.db.daos.TodoRepository
import com.perspectivev.todo.db.entities.LatestUiState
import com.perspectivev.todo.db.entities.Todo
import com.perspectivev.todo.db.entities.TodoViewModel
import com.perspectivev.todo.ui.theme.TodosTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var _db: AppDatabase
    private lateinit var _viewModel: TodoViewModel
    private lateinit var _todoRepository: TodoRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _db = AppDatabase.getDbInstance(this.applicationContext)
        _todoRepository = TodoRepository(_db.todoDao(), _db.todoDao().getAll())
        _viewModel = TodoViewModel(_todoRepository)

        val todos = mutableStateListOf<Todo>()
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                _viewModel.uiState.collectLatest { uiState ->
                    // New value received
                    when (uiState) {
                        is LatestUiState.Success -> todos.addAll(uiState.todos)
                        is LatestUiState.Error -> print(uiState.exception)
                    }
                }
            }
        }

        setContent {
            TodosTheme {
                Scaffold {
                    TodoList(Modifier.padding(it))
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(modifier: Modifier) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val db by lazy { AppDatabase.getDbInstance(context) }
    val repository by lazy { TodoRepository(db.todoDao()) }
//    val viewmodel  = viewModel<TodoViewModel>(
//        factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return TodoViewModel(repository) as T
//            }
//        }
//    )
    var todoModel by remember {
        val td = Todo(
            0,
            "",
            null,
            isDone = false,
            isDeleted = false
        )

        mutableStateOf(td)
    }

    var text by rememberSaveable { mutableStateOf("") }
//    var searching by remember { mutableStateOf(false) }

    val todosStates = MutableStateFlow<SnapshotStateList<Todo>>(mutableStateListOf())

    val todosSnapshotList = if (LocalInspectionMode.current) {
        val allTodos: SnapshotStateList<Todo> = mutableStateListOf()
        for (i in 1..5) {
            allTodos.add(Todo(i, "Test $i", null, isDone = false, isDeleted = false))
        }
        allTodos
    } else {
        val allTodos by repository.allItems.collectAsState(initial = emptyList())
        allTodos.toMutableStateList()
    }

    todosStates.update { list ->
        list.addAll(todosSnapshotList)
        list
    }

    val todos by todosStates.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    )
    {

        /* Input Row */
        Row(
            modifier = Modifier
                .padding(5.dp, 2.dp)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter task ...") },
                modifier = Modifier
                    .weight(1.6f, true)
                    .padding(2.dp, 0.dp)
                    .wrapContentWidth()
            )
            /* Add Button */
            OutlinedButton(
                onClick = {
                    if (text == "") {
                        Toast.makeText(context, "Please enter task", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutine.launch {
                            if (todoModel.id == 0) {
                                val todo = Todo(0, text, "", isDone = false, isDeleted = false)
                                repository.insert(todo)
                            } else {
                                todoModel.task = text
                                repository.update(todoModel)
                            }
                            text = ""
                            todoModel = Todo(0, text, "", isDone = false, isDeleted = false)
                        }
                    }

                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(64.dp)
                    .padding(4.dp, 7.dp, 0.dp, 0.dp)
                    .border(
                        BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        RoundedCornerShape(5.dp)
                    )
                    .weight(0.5f, true)
            )
            {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Save Todo")
            }
        }


        /* Items List */
        LazyColumn(
            modifier = Modifier
                .padding(5.dp, 5.dp)
                .fillMaxSize()
                .padding(0.dp)
        )
        {
            itemsIndexed(items = todos, key = { index, it -> it.id }) { index, it ->
                ListItem(
                    modifier = Modifier
                        .padding(2.dp, 5.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(corner = CornerSize(8.dp))
                        )
                        .padding(0.dp)
                        .clickable {
                            text = it.task
                            todoModel = it
                        },
                    headlineText = { Text(it.task) },
                    leadingContent = {
                        Checkbox(
                            checked = it.isDone,
                            onCheckedChange = { isChecked ->
                                // TODO: need to see why stat doesn't update here 
                                it.isDone = isChecked
                                coroutine.launch {
                                    todosStates.getAndUpdate {
                                        it[index].isDone = isChecked
                                        todosStates.emit(it)
                                        it
                                    }
                                    repository.update(it)
                                    text = ""
                                }
                            })
                    },
                    trailingContent = {
                        IconButton(onClick = {
                            coroutine.launch {
                                val isDone = !it.isDone
                                it.isDone = isDone
                                repository.delete(it)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Save Todo",
                                tint = Color(0xFFEA7171)
                            )
                        }
                    }
                )
            }
            // for single item use item {}
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
//@Preview(name = "Full Preview", showSystemUi = true)
@Composable
fun ToDosPreview() {
    TodosTheme {
        TodoList(Modifier.padding())
    }
}
