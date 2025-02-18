package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                TaskManager()
        }
    }
}
    // Main composable that sets up the UI
    @Composable
    fun TaskManager(modifier:Modifier = Modifier) {

        // Create a list of TaskModel objects in state
        val list = remember {
            List(1){i -> TaskModel(i, "Task # $i") }.toMutableStateList()
        }
        // Set up the UI using Scaffold and Column
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {AddButton(list = list)}
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ) {
                // Display the title calling the Title Composable
                Title()
                // If the list is empty, we display a message indicating no tasks
                if (list.isEmpty())
                    Text(text = "No Tasks at the moment.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth())
                else
                    // Otherwise, we display the list of tasks in a LazyColumn
                LazyColumn(
                    modifier = modifier
                ) {
                    items(list) { item ->
                        Task(task = item, removeTask = { task -> list.remove(task) })
                    }
                }

            }
        }
    }

// Composable for a single task
@Composable
fun Task(task: TaskModel, removeTask: (TaskModel) -> Unit,) {
    var text by remember { mutableStateOf(task.text) }
    Surface(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(vertical = 5.dp)) {
            TextField(
                value = text,
                onValueChange = { text = it },
            )
            Button(onClick = { removeTask(task) }) {
                Text(text = "X")
            }
        }
    }
}
    // Composable for the title
    @Composable
    fun Title() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
                Text(
                    text = "Task Manager",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(24.dp),
                    textAlign = TextAlign.Center,
                )
        }

    }
// Composable for the add button
@Composable
fun AddButton(list: MutableList<TaskModel>) {
        Button(onClick = {
                val newId = list[list.size - 1].id + 1
                val newTask = TaskModel(newId, "Task # $newId")
                list.add(newTask)
                         },
            modifier = Modifier
                .padding(20.dp)
                .height(50.dp)) {
            Text(
                text = "New",
                fontSize = 16.sp
            )
        }
}
    // Data class representing a task
    data class TaskModel(val id: Int, val text: String)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskManagerTheme {
        //AddButton()
    }
}}