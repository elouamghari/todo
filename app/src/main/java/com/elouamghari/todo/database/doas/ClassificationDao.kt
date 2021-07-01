package com.elouamghari.todo.database.doas

import androidx.room.*
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.relations.ClassificationWithTodos
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassificationDao {

    @Insert
    suspend fun insert(classification : Classification)

    @Update
    suspend fun update(classification: Classification)

    @Delete
    suspend fun delete(classification: Classification)

    @Query("SELECT * FROM classification")
    fun getClassifications(): Flow<List<Classification>>

    @Query("SELECT * FROM classification WHERE id = :classificationId")
    fun getClassification(classificationId: Int): Flow<Classification>

    @Transaction
    @Query("SELECT * FROM classification WHERE id = :classificationId")
    fun getClassificationWithTodos(classificationId: Int): Flow<ClassificationWithTodos>

}