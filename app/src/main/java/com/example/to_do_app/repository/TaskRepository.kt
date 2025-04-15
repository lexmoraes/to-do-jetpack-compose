package com.example.to_do_app.repository

import com.example.to_do_app.datasource.DataSource
import com.example.to_do_app.model.TaskModel

class TaskRepository {
    private val dataSource: DataSource = DataSource()

    fun saveTask(taskModel: TaskModel) {
        dataSource.saveTask(taskModel)
    }

}