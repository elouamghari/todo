package com.elouamghari.todo.ui.classifications.list.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.relations.ClassificationWithTodos
import com.elouamghari.todo.database.repositories.ClassificationRepository
import kotlinx.coroutines.launch

class ClassificationViewModel(private val repository: ClassificationRepository) : ViewModel() {

    val classifications: LiveData<List<Classification>> =
        repository.classifications.asLiveData()

    fun getClassification(classificationId: Int): LiveData<Classification?> =
        repository.getClassification(classificationId).asLiveData()

    fun getClassificationWithTodos(classificationId: Int): LiveData<ClassificationWithTodos?> =
        repository.getClassificationWithTodos(classificationId).asLiveData()

    fun insert(classification: Classification) = viewModelScope.launch {
        repository.insert(classification)
    }

    fun update(classification: Classification) = viewModelScope.launch {

        repository.update(classification)
    }

    fun delete(classification: Classification) = viewModelScope.launch {
        repository.delete(classification)
    }
}