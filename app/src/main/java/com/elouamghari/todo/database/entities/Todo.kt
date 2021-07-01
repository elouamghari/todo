package com.elouamghari.todo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "todo", foreignKeys = [
    ForeignKey(
        entity = Classification::class,
        parentColumns = ["id"],
        childColumns = ["classification_id"],
        onDelete = CASCADE
    )
])
data class Todo(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "order") var order: Int,
    @ColumnInfo(name = "classification_id") var classificationId: Int,
    @ColumnInfo(name = "is_accomplished") var isAccomplished: Boolean,
    @ColumnInfo(name = "description") var description: String = ""
)