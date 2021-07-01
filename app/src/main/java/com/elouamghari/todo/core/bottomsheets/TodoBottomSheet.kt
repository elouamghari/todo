package com.elouamghari.todo.core.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.elouamghari.todo.R
import com.elouamghari.todo.application.MainApplication
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.databinding.BottomSheetTodoBinding
import com.elouamghari.todo.ui.classifications.list.constants.ClassificationArgumentsKeys
import com.elouamghari.todo.ui.classifications.list.viewmodels.ClassificationViewModel
import com.elouamghari.todo.ui.classifications.list.viewmodels.factories.ClassificationViewModelFactory
import com.elouamghari.todo.ui.todos.list.viewmodels.TodoViewModel
import com.elouamghari.todo.ui.todos.list.viewmodels.factories.TodoViewModelFactory
import com.elouamghari.todo.utils.getItemPosition
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class TodoBottomSheet : BottomSheetDialogFragment() {

    protected abstract fun isEdit(): Boolean

    protected lateinit var binding: BottomSheetTodoBinding
    protected var classificationId: Int? = null
    protected val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory(MainApplication.instance.todoRepository)
    }
    protected val classificationViewModel: ClassificationViewModel by viewModels {
        ClassificationViewModelFactory(MainApplication.instance.classificationRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classificationId = arguments?.getInt(ClassificationArgumentsKeys.CLASSIFICATION_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetTodoBinding.inflate(inflater)
        binding.isEdit = isEdit()
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        classificationViewModel.classifications
            .observe(viewLifecycleOwner, this::observeClassifications)
        binding.classificationsSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                classificationId = classificationViewModel.classifications.value?.get(position)?.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun observeClassifications(classifications: List<Classification>?){
        classifications?.let{
            ArrayAdapter(requireContext(), R.layout.spinner_classification, it).also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_item_classification)
                with(binding) {
                    binding.classificationsSpinner.adapter = adapter
                    classificationsSpinner.setSelection(classifications.getItemPosition(classificationId))
                }
            }
        }
    }

    fun show(manager: FragmentManager){
        if (!this.isAdded){
            show(manager, "TAG")
        }
    }
}