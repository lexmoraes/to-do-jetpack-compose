package com.example.to_do_app.datasource

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences.Key
import com.example.to_do_app.dataStore
import com.example.to_do_app.model.TaskModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DataSource {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    fun saveTask(taskModel: TaskModel) {
        val taskPayload = mapOf(
            "title" to taskModel.title,
            "description" to taskModel.description,
            "priority" to taskModel.priority,
            "createdAt" to now.date
        )
        firestore
            .collection("tarefas")
            .document("$now-${taskModel.title}")
            .set(taskPayload)
            .addOnSuccessListener{Log.d("Firestore", "Documento salvo: $now-${taskModel.title}") }
            .addOnFailureListener{Log.d("Firestore", "Erro ao salvar documento: $now-${taskModel.title}") }
            .addOnCanceledListener{Log.d("Firestore", "A ação foi cancelada:")}
            .addOnCompleteListener{Log.d("Firestore", "Ação finalizada:")}
    }
}

class PreferencesDataStore(private val context: Context) {

    fun <T> getPreference(key: Key<T>): Flow<T?> {
        return context.dataStore.data.map { preferences -> preferences[key] }
    }

    suspend fun <T> setPreference(key: Key<T>, value: T) {
        context.dataStore.edit { preferences -> preferences[key] = value}
    }
}