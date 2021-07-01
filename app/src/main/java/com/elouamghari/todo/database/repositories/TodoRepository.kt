package com.elouamghari.todo.database.repositories

import androidx.annotation.WorkerThread
import com.elouamghari.todo.database.doas.TodoDao
import com.elouamghari.todo.database.entities.Todo
import com.elouamghari.todo.database.relations.TodoAndClassification
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    fun getClassificationTodos(classificationId: Int): Flow<List<Todo>> =
        todoDao.getClassificationTodos(classificationId)

    fun getTodo(todoId: Int): Flow<Todo> = todoDao.getTodo(todoId)

    fun getTodoAndClassification(todoId: Int): Flow<TodoAndClassification> =
        todoDao.getTodoAndClassification(todoId)

    @WorkerThread
    suspend fun insert(todo: Todo) = todoDao.insert(todo)

    @WorkerThread
    suspend fun update(todo: Todo) = todoDao.update(todo)

    @WorkerThread
    suspend fun update(todos: List<Todo>) = todoDao.update(todos)

    @WorkerThread
    suspend fun delete(todo: Todo) = todoDao.delete(todo)

}