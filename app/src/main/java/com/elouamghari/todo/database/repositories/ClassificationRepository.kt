package com.elouamghari.todo.database.repositories

import androidx.annotation.WorkerThread
import com.elouamghari.todo.database.doas.ClassificationDao
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.relations.ClassificationWithTodos
import kotlinx.coroutines.flow.Flow

class ClassificationRepository(private val classificationDao: ClassificationDao) {

    val classifications: Flow<List<Classification>> = classificationDao.getClassifications()

    fun getClassification(classificationId: Int) : Flow<Classification>{
        return classificationDao.getClassification(classificationId)
    }

    fun getClassificationWithTodos(classificationId: Int) : Flow<ClassificationWithTodos>{
        return classificationDao.getClassificationWithTodos(classificationId)
    }

    @WorkerThread
    suspend fun insert(classification: Classification){
        classificationDao.insert(classification)
    }

    @WorkerThread
    suspend fun update(classification: Classification){
        classificationDao.update(classification)
    }

    @WorkerThread
    suspend fun delete(classification: Classification){
        classificationDao.delete(classification)
    }
}