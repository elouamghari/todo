package com.elouamghari.todo.database.doas

import androidx.room.*
import com.elouamghari.todo.database.entities.Todo
import com.elouamghari.todo.database.relations.TodoAndClassification
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Update
    suspend fun update(todos: List<Todo>)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todo WHERE classification_id = :classificationId")
    fun getClassificationTodos(classificationId: Int): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :todoId")
    fun getTodo(todoId: Int): Flow<Todo>

    @Transaction
    @Query("SELECT * FROM todo WHERE id = :todoId")
    fun getTodoAndClassification(todoId: Int): Flow<TodoAndClassification>
}