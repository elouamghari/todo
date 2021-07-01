package com.elouamghari.todo.ui.todos.edit

import android.os.Bundle
import android.view.View
import com.elouamghari.todo.R
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.entities.Todo
import com.elouamghari.todo.database.relations.TodoAndClassification
import com.elouamghari.todo.utils.getItemPosition
import com.elouamghari.todo.core.bottomsheets.TodoBottomSheet
import com.elouamghari.todo.ui.todos.list.constants.TodoArgumentsKeys

class EditTodoBottomSheet : TodoBottomSheet() {

    private var todoId: Int? = null
    private var todo: Todo? = null
    private var classification: Classification? = null

    override fun isEdit(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoId = arguments?.getInt(TodoArgumentsKeys.TODO_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUiFields()
        binding.saveButton.setOnClickListener { editTodo() }
        binding.deleteButton.setOnClickListener { deleteTodo() }
    }

    private fun initializeUiFields(){
        todoId?.let{todoId->
            todoViewModel.getTodoAndClassification(todoId)
                .observe(viewLifecycleOwner, this::observeTodoAndClassification)
        }
    }

    private fun observeTodoAndClassification(todoAndClassification: TodoAndClassification?){
        observeTodo(todoAndClassification?.todo)
        observeClassification(todoAndClassification?.classification)
    }

    private fun observeTodo(todo: Todo?){
        this.todo = todo
        binding.nameEditText.setText(todo?.name)
        binding.descriptionEditText.setText(todo?.description)
    }

    private fun observeClassification(classification: Classification?){
        this.classification = classification
        this.classificationId = classification?.id
        val position: Int = classificationViewModel.classifications.value
            .getItemPosition(classification?.id)
        binding.classificationsSpinner.setSelection(position)
    }

    private fun createTodoFromFields(classificationId: Int?): Todo?{
        val newName = binding.nameEditText.text.toString().trim()
        if (newName.isBlank()){
            binding.nameEditText.error = getString(R.string.required_field)
            return null
        }
        todo?.apply {
            name = newName
            description = binding.descriptionEditText.text.toString()
        }
        classificationId?.let{
            todo?.classificationId = it
        }
        return todo
    }

    private fun editTodo(){
        createTodoFromFields(classificationId)?.let{todo ->
            todoViewModel.update(todo)
            dismiss()
        }
    }

    private fun deleteTodo(){
        todo?.let{todo ->
            todoViewModel.delete(todo)
            dismiss()
        }
    }
}