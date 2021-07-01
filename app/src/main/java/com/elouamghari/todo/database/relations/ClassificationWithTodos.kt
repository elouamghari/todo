package com.elouamghari.todo.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.entities.Todo

data class ClassificationWithTodos(
    @Embedded var classification: Classification,
    @Relation(
        parentColumn = "id",
        entityColumn = "classification_id"
    )
    var todos: List<Todo>
)