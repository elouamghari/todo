package com.elouamghari.todo.ui.todos.list.viewmodels

import androidx.lifecycle.*
import com.elouamghari.todo.database.entities.Todo
import com.elouamghari.todo.database.relations.TodoAndClassification
import com.elouamghari.todo.database.repositories.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository): ViewModel() {

    fun getClassificationTodos(classificationId: Int): LiveData<List<Todo>?>{
        return repository.getClassificationTodos(classificationId).asLiveData()
    }

    fun getTodo(todoId: Int): LiveData<Todo?>{
        return repository.getTodo(todoId).asLiveData()
    }

    fun getTodoAndClassification(todoId: Int): LiveData<TodoAndClassification?>{
        return repository.getTodoAndClassification(todoId).asLiveData()
    }

    fun insert(todo: Todo) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: Todo) = viewModelScope.launch {
        repository.update(todo)
    }

    fun update(todos: List<Todo>) = viewModelScope.launch {
        repository.update(todos)
    }

    fun delete(todo: Todo) = viewModelScope.launch {
        repository.delete(todo)
    }

}