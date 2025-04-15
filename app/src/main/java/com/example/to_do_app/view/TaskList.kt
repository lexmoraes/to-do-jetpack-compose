@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.to_do_app.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.intro.view.TaskItem
import com.example.to_do_app.PreferencesKeys
import com.example.to_do_app.dataStore
import com.example.to_do_app.datasource.PreferencesDataStore
import com.example.to_do_app.model.TaskModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val isDarkModeFlow = remember {
        context.dataStore.data.map { preferences ->
                preferences[PreferencesKeys.IS_DARK_MODE] ?: false
            }
    }
    val isDarkMode by isDarkModeFlow.collectAsState(initial = false)

    val taskList: MutableList<TaskModel> = mutableListOf(
        TaskModel("Comida para o cachorro", "Comprar comida da Lilly", 1),
        TaskModel("Estudar Kotlin", "Revisar conteúdo da aula", 3),
        TaskModel("Comprar passagem", "Comprar passagem de avião para NY", 0),
        TaskModel("Reprovar no curso", "Tirar zero nas provas", 3),
        TaskModel("Fazer o TCC", "Dormir pouco e escrever essa parada", 3),
        TaskModel("Reservar hotel da viagem do FDS", "Reservar hotel de selva para o FDS", 2),
        TaskModel("Tirar 10 na prova de Kotlin", "Estudar 1 dia antes", 0),
    )
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    "Lista de tarefas", fontSize = 28.sp, fontWeight = FontWeight.Bold
                )
            }, colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = Color.White
            ), actions = {
                Switch(
                    checked = isDarkMode, onCheckedChange = { isChecked ->
                        scope.launch {
                            PreferencesDataStore(context).setPreference(
                                PreferencesKeys.IS_DARK_MODE, isChecked
                            )
                        }
                    })
            })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate("saveTask")
            }, containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
        Button(onClick = { navController.navigate("clickCounter") }) {
            Text("Ir para Contador")
        }

    }


    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(taskList) {
                TaskItem(it)
            }
        }
    }
}