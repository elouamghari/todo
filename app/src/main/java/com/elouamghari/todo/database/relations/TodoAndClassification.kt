package com.elouamghari.todo.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.entities.Todo

data class TodoAndClassification(
    @Embedded var todo: Todo,
    @Relation(
        parentColumn = "classification_id",
        entityColumn = "id"
    )
    var  classification: Classification
)