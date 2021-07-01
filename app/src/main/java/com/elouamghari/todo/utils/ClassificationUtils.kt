package com.elouamghari.todo.utils

import com.elouamghari.todo.database.entities.Classification

fun List<Classification>?.getItemPosition(classificationId: Int?): Int{
    var position = 0
    classificationId?.let{
        this?.forEachIndexed { index, classification ->
            if (classification.id == it) {
                position = index
                return@forEachIndexed
            }
        }
    }
    return position
}