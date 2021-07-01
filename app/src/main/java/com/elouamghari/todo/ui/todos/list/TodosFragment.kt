package com.elouamghari.todo.ui.todos.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elouamghari.todo.application.MainApplication
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.entities.Todo
import com.elouamghari.todo.database.relations.ClassificationWithTodos
import com.elouamghari.todo.databinding.FragmentTodosBinding
import com.elouamghari.todo.generic.GenericObserver
import com.elouamghari.todo.ui.classifications.list.constants.ClassificationArgumentsKeys
import com.elouamghari.todo.ui.classifications.edit.EditClassificationBottomSheet
import com.elouamghari.todo.ui.classifications.list.viewmodels.ClassificationViewModel
import com.elouamghari.todo.ui.classifications.list.viewmodels.factories.ClassificationViewModelFactory
import com.elouamghari.todo.ui.todos.add.AddTodoBottomSheet
import com.elouamghari.todo.ui.todos.edit.EditTodoBottomSheet
import com.elouamghari.todo.ui.todos.list.adapters.TodosAdapter
import com.elouamghari.todo.ui.todos.list.constants.TodoArgumentsKeys
import com.elouamghari.todo.ui.todos.list.viewmodels.TodoViewModel
import com.elouamghari.todo.ui.todos.list.viewmodels.factories.TodoViewModelFactory

class TodosFragment : Fragment() {

    private lateinit var binding: FragmentTodosBinding
    private var classificationId: Int? = null
    private val adapter: TodosAdapter = TodosAdapter()
    private val classificationViewModel: ClassificationViewModel by viewModels{
        ClassificationViewModelFactory(MainApplication.instance.classificationRepository)
    }
    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory(MainApplication.instance.todoRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classificationId = arguments?.getInt(ClassificationArgumentsKeys.CLASSIFICATION_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTodosBinding.inflate(inflater)
        binding.todosRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        adapter.setListItemCheckChangedListener{todo ->
            todoViewModel.update(todo)
        }
        adapter.setOrderChangedListener{todos->
            todosObserver.disableObserverForNext()
            //todos.reorderTodos()
            todoViewModel.update(todos)
        }
        adapter.setTodoEditListener(this::editTodo)
        binding.addTodoButton.setOnClickListener { createTodo() }
        binding.editClassificationButton.setOnClickListener{ editClassification() }
    }

    private fun initializeUI(){
        classificationId?.let { classificationId ->
            classificationViewModel.getClassificationWithTodos(classificationId)
                .observe(viewLifecycleOwner, todosObserver)
        }
    }

    private val todosObserver = object : GenericObserver<ClassificationWithTodos?>(){
        override fun onDataChanged(classificationWithTodos: ClassificationWithTodos?) {
            observeClassificationWithTodos(classificationWithTodos)
        }
    }

    private fun observeClassificationWithTodos(classificationWithTodos: ClassificationWithTodos?){
        observeClassification(classificationWithTodos?.classification)
        observeTodos(classificationWithTodos?.todos)
    }

    private fun observeClassification(classification: Classification?){
        binding.bigTitleTextView.text = classification?.name
    }

    private fun observeTodos(todos: List<Todo>?) {
        val orderedTodos = todos?.sortedBy{todo ->
            todo.order
        }
        adapter.submitList(orderedTodos)
    }

    private fun createTodo(){
        classificationId?.let {
            val args = Bundle()
            val addTodo = AddTodoBottomSheet((adapter.itemCount+1))
            args.putInt(ClassificationArgumentsKeys.CLASSIFICATION_ID, it)
            addTodo.arguments = args
            addTodo.show(parentFragmentManager)
        }
    }

    private fun editClassification() {
        classificationId?.let {
            val args = Bundle()
            val editClassification = EditClassificationBottomSheet()
            args.putInt(ClassificationArgumentsKeys.CLASSIFICATION_ID, it)
            editClassification.arguments = args
            editClassification.show(parentFragmentManager)
        }
    }

    private fun editTodo(todo: Todo){
        todo.id?.let {todoId->
            val args = Bundle()
            args.putInt(TodoArgumentsKeys.TODO_ID, todoId)
            val editTodo = EditTodoBottomSheet()
            editTodo.arguments = args
            editTodo.show(parentFragmentManager)
        }
    }
}