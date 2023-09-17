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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.perspectivev.todo.db.AppDatabase
import com.perspectivev.todo.db.daos.TodoRepository
import com.perspectivev.todo.db.entities.Todo
import com.perspectivev.todo.db.entities.TodoViewModel
import com.perspectivev.todo.ui.theme.TodosTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val context = LocalContext.current
    val db by lazy { AppDatabase.getDbInstance(context) }
    val repository by lazy { TodoRepository(db.todoDao()) }
    val viewModel = viewModel<TodoViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TodoViewModel(repository) as T
            }
        }
    )
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

//     Collect the state from the StateFlow using collectAsState
    val todos by viewModel.getTodosList(isInspectionMode = LocalInspectionMode.current)
        .collectAsState(emptyList())

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
                        if (todoModel.id == 0) {
                            val todo = Todo(0, text, "", isDone = false, isDeleted = false)
                            viewModel.insert(todo)
                        } else {
                            todoModel.task = text
                            val updatedList = todos.toMutableList()
                            val indexOfTodo = updatedList.indexOf(todoModel)
                            updatedList[indexOfTodo] = todoModel
                            viewModel.update(todoModel, updatedList)
                        }
                        text = ""
                        todoModel = Todo(0, text, "", isDone = false, isDeleted = false)
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
            itemsIndexed(items = todos, key = { _, it -> it.id }) { index, it ->
                ListItem(
                    modifier = Modifier
                        .padding(2.dp, 5.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(corner = CornerSize(8.dp))
                        )
                        .clickable {
                            text = it.task
                            todoModel = it
                        },
                    headlineText = { Text(it.task) },
                    leadingContent = {
                        Checkbox(
                            checked = it.isDone,
                            onCheckedChange = { isChecked ->
                                it.isDone = isChecked
                                val updatedList = todos.toMutableList()
                                updatedList[index].isDone = isChecked
                                viewModel.update(it, updatedList)
                                text = ""
                            })
                    },
                    trailingContent = {
                        IconButton(onClick = {
                            viewModel.delete(it)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Save Todo",
                                tint = Color(0xFFEA7171)
                            )
                        }
                    }
                )

//                if (index < todos.lastIndex)
//                    Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
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
