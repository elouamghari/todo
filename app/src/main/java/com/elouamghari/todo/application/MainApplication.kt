package com.elouamghari.todo.application

import android.app.Application
import com.elouamghari.todo.database.AppDatabase
import com.elouamghari.todo.database.repositories.ClassificationRepository
import com.elouamghari.todo.database.repositories.TodoRepository

/**
 * Created by Mohamed Elouamghari on 17/06/2021
 */
class MainApplication : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getAppDatabase()
    }

    val classificationRepository: ClassificationRepository by lazy {
        ClassificationRepository(database.getClassificationDao())
    }

    val todoRepository: TodoRepository by lazy {
        TodoRepository(database.getTodoDao())
    }

    companion object{
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}