package com.elouamghari.todo.ui.classifications.list.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elouamghari.todo.database.repositories.ClassificationRepository
import com.elouamghari.todo.ui.classifications.list.viewmodels.ClassificationViewModel

class ClassificationViewModelFactory(private val repository: ClassificationRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClassificationViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ClassificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}