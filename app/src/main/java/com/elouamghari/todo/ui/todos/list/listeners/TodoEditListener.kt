package com.elouamghari.todo.ui.todos.list.listeners

import com.elouamghari.todo.database.entities.Todo

fun interface TodoEditListener {
    fun onTodoEdit(todo: Todo)
}