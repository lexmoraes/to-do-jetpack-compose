package com.example.to_do_app.view

import MyTextField
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.to_do_app.components.CustomButton
import com.example.to_do_app.model.Priority
import com.example.to_do_app.model.TaskModel
import com.example.to_do_app.repository.TaskRepository
import com.example.to_do_app.ui.theme.GreenRadioButtonDisabled
import com.example.to_do_app.ui.theme.GreenRadioButtonSelected
import com.example.to_do_app.ui.theme.PurpleGrey40
import com.example.to_do_app.ui.theme.RedRadioButtonDisabled
import com.example.to_do_app.ui.theme.RedRadioButtonSelected
import com.example.to_do_app.ui.theme.YellowRadioButtonDisabled
import com.example.to_do_app.ui.theme.YellowRadioButtonSelected
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTask(navController: NavController) {
    var taskTitle by remember { mutableStateOf(value = "") }
    var taskDescription by remember { mutableStateOf(value = "") }
    var taskPriority by remember { mutableIntStateOf(Priority.NO_PRIORITY.value) }
    var lowPriority by remember { mutableStateOf(value = false) }
    var mediumPriority by remember { mutableStateOf(value = false) }
    var highPriority by remember { mutableStateOf(value = false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Salvar tarefa",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = topAppBarColors(containerColor = PurpleGrey40)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            MyTextField(
                value = taskTitle,
                onValueChange = {
                    taskTitle = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Título da tarefa",
                maxLines = 1,
                keyboardType = KeyboardType.Text,
            )

            MyTextField(
                value = taskDescription,
                onValueChange = {
                    taskDescription = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Descrição",
                maxLines = 5,
                keyboardType = KeyboardType.Text,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Nível de prioridade")
                RadioButton(
                    selected = lowPriority,
                    onClick = {
                        lowPriority = !lowPriority
                        mediumPriority = false
                        highPriority = false
                    },
                    colors = RadioButtonColors(
                        unselectedColor = GreenRadioButtonDisabled,
                        selectedColor = GreenRadioButtonSelected,
                        disabledSelectedColor = Color.Unspecified,
                        disabledUnselectedColor = Color.Unspecified
                    )
                )
                RadioButton(
                    selected = mediumPriority,
                    onClick = {
                        lowPriority = false
                        mediumPriority = !mediumPriority
                        highPriority = false
                    },
                    colors = RadioButtonColors(
                        unselectedColor = YellowRadioButtonDisabled,
                        selectedColor = YellowRadioButtonSelected,
                        disabledSelectedColor = Color.Unspecified,
                        disabledUnselectedColor = Color.Unspecified
                    )
                )
                RadioButton(
                    selected = highPriority,
                    onClick = {
                        lowPriority = false
                        mediumPriority = false
                        highPriority = !highPriority
                    },
                    colors = RadioButtonColors(
                        unselectedColor = RedRadioButtonDisabled,
                        selectedColor = RedRadioButtonSelected,
                        disabledSelectedColor = Color.Unspecified,
                        disabledUnselectedColor = Color.Unspecified
                    )
                )
            }
            CustomButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(10.dp),
                buttonColor = Color.Blue,
                label = "Salvar",
                contentColor = TODO()
            )
        }
    }
}

fun onClickSaveButton(
    scope: CoroutineScope,
    context: Context,
    taskModel: TaskModel
) {
    val taskRepository = TaskRepository()
    var isValid = true

    scope.launch(Dispatchers.IO) {
        isValid = taskModel.title!!.isEmpty() && taskModel.description!!.isEmpty()
        taskRepository.saveTask(taskModel)
    }

    scope.launch(Dispatchers.Main) {
        if (isValid) {
            Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            taskModel.title?.let {
                if (it.isEmpty()) {
                    Toast.makeText(context, "Título é obrigatório!", Toast.LENGTH_SHORT).show()
                } else taskModel.description?.let {
                    if (it.isEmpty()) {
                        Toast.makeText(context, "Descrição é obrigatória!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
