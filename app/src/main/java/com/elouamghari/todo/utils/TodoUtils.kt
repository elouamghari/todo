package com.elouamghari.todo.utils

import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.entities.Todo

fun List<Todo>.order(): List<Todo>{
    val ordered : MutableList<Todo> = ArrayList()

    ordered += this.filter{
        !it.isAccomplished
    }.sortedBy {
        it.order
    }

    ordered += this.filter{
        it.isAccomplished
    }.sortedBy {
        it.order
    }

    return ordered
}

