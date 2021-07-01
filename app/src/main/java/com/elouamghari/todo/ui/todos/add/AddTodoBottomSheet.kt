package com.elouamghari.todo.ui.todos.add

import android.os.Bundle
import android.view.View
import com.elouamghari.todo.R
import com.elouamghari.todo.database.entities.Todo
import com.elouamghari.todo.core.bottomsheets.TodoBottomSheet

class AddTodoBottomSheet(private val order: Int) : TodoBottomSheet() {

    override fun isEdit(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener { createTodo() }
    }

    private fun createTodo(){
        classificationId?.let { classificationId ->
            createTodoFromFields(classificationId)?.let { todo ->
                todoViewModel.insert(todo)
                dismiss()
            }
        }
    }

    private fun createTodoFromFields(classificationId: Int): Todo?{
        val newName = binding.nameEditText.text.toString().trim()
        if (newName.isBlank()){
            binding.nameEditText.error = getString(R.string.required_field)
            return null
        }
        return Todo(
            name = newName,
            order = order,
            classificationId = classificationId,
            description = binding.descriptionEditText.text.toString(),
            isAccomplished = false
        )
    }
}