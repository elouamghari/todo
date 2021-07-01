package com.elouamghari.todo.ui.classifications.edit

import android.os.Bundle
import android.view.View
import com.elouamghari.todo.R
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.managers.NavigationManager
import com.elouamghari.todo.core.bottomsheets.ClassificationBottomSheet
import com.elouamghari.todo.ui.classifications.list.constants.ClassificationArgumentsKeys

class EditClassificationBottomSheet : ClassificationBottomSheet() {

    private var classificationId: Int? = null
    private var classification: Classification? = null

    override fun isEdit(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classificationId = arguments?.getInt(ClassificationArgumentsKeys.CLASSIFICATION_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        binding.saveButton.setOnClickListener { updateClassification() }
        binding.deleteButton.setOnClickListener { deleteClassification() }
    }

    private fun initializeUI(){
        classificationId?.let { classificationId ->
            classificationViewModel.getClassification(classificationId)
                .observe(viewLifecycleOwner, this::observeClassification)
        }
    }

    private fun observeClassification(classification: Classification?){
        this.classification = classification
        binding.nameEditText.setText(classification?.name)
        binding.descriptionEditText.setText(classification?.description)
    }

    private fun createClassificationFromFields(): Classification?{
        val newName = binding.nameEditText.text.toString().trim()
        if (newName.isBlank()){
            binding.nameEditText.error = getString(R.string.required_field)
            return null
        }
        classification?.apply {
            name = newName
            description = binding.descriptionEditText.text.toString()
        }
        return classification
    }

    private fun updateClassification() {
        createClassificationFromFields()?.let {classification ->
            classificationViewModel.update(classification)
            dismiss()
        }
    }

    private fun deleteClassification() {
        classification?.let {cassification ->
            classificationViewModel.delete(cassification)
            dismiss()
            NavigationManager.popBackStack(requireActivity())
        }
    }

}