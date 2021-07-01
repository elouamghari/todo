package com.elouamghari.todo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classification")
data class Classification(
    @PrimaryKey @ColumnInfo(name = "id") var id :Int? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String = ""
){
    override fun toString(): String {
        return name
    }
}