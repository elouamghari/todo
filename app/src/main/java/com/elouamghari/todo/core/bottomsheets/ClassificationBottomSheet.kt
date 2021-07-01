package com.elouamghari.todo.core.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.elouamghari.todo.application.MainApplication
import com.elouamghari.todo.databinding.BottomSheetClassificationBinding
import com.elouamghari.todo.ui.classifications.list.viewmodels.ClassificationViewModel
import com.elouamghari.todo.ui.classifications.list.viewmodels.factories.ClassificationViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ClassificationBottomSheet: BottomSheetDialogFragment() {

    protected lateinit var binding: BottomSheetClassificationBinding
    protected val classificationViewModel: ClassificationViewModel by viewModels {
        ClassificationViewModelFactory(MainApplication.instance.classificationRepository)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected abstract fun isEdit(): Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetClassificationBinding.inflate(inflater)
        binding.isEdit = isEdit()
        return binding.root
    }

    fun show(manager: FragmentManager){
        if (!this.isAdded){
            show(manager, "TAG")
        }
    }
}