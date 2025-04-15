package com.example.to_do_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.to_do_app.datasource.PreferencesDataStore
import com.example.to_do_app.ui.theme.To_do_appTheme
import com.example.to_do_app.view.LoginScreen
import com.example.to_do_app.view.SaveTask
import com.example.to_do_app.view.TaskList
import com.example.to_do_app.view.WelcomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object PreferencesKeys {
    val IS_DARK_MODE = booleanPreferencesKey("darkMode")
    val CLICK_COUNT = intPreferencesKey("click_count")

}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkModeFlow = remember {
                PreferencesDataStore(context = this).getPreference(
                    PreferencesKeys.IS_DARK_MODE
                )
            }
            val isDarkMode by isDarkModeFlow.collectAsState(initial = false)

            MaterialTheme {
                ClickCounterScreen()
            }
            To_do_appTheme (darkTheme = isDarkMode ?: false) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "welcome") {
                    composable(route = "welcome") { WelcomeScreen(navController) }
                    composable(route = "login") { LoginScreen(navController) }
                    composable(route = "taskList") { TaskList(navController) }
                    composable(route = "saveTask") { SaveTask(navController) }
                    composable("ClickCounter") { ClickCounterScreen() }
                }
            }
        }
    }
}

@Composable
fun ClickCounterScreen() {
    val context = LocalContext.current
    val dataStore = remember { PreferencesDataStore(context) }

    var clickCount by remember { mutableStateOf(0) }

    // Recuperar o valor salvo ao abrir a tela
    LaunchedEffect(Unit) {
        dataStore.getPreference(PreferencesKeys.CLICK_COUNT).collect { count ->
            clickCount = count ?: 0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cliques: $clickCount", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            clickCount++
            CoroutineScope(Dispatchers.IO).launch {
                dataStore.setPreference(PreferencesKeys.CLICK_COUNT, clickCount)
            }
        }) {
            Text("Clique aqui")
        }
    }
}