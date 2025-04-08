package com.example.to_do_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.to_do_app.ui.theme.To_do_appTheme
import com.example.to_do_app.view.SaveTask
import com.example.to_do_app.view.TaskList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            To_do_appTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "taskList") {
                        composable(route = "taskList") { TaskList(navController) }
                        composable(route = "saveTask") { SaveTask(navController) }
                }

            }
        }
    }
}
