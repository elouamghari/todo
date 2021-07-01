package com.elouamghari.todo.ui.classifications.add

import android.os.Bundle
import android.view.View
import com.elouamghari.todo.R
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.core.bottomsheets.ClassificationBottomSheet

class AddClassificationBottomSheet : ClassificationBottomSheet() {

    override fun isEdit(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener { createClassification() }
    }

    private fun createClassification() {
        createClassificationFromFields()?.let{classification ->
            classificationViewModel.insert(classification)
            dismiss()
        }
    }

    private fun createClassificationFromFields(): Classification?{
        val newName = binding.nameEditText.text.toString().trim()
        if (newName.isBlank()){
            binding.nameEditText.error = getString(R.string.required_field)
            return null
        }
        return Classification(
            name = newName,
            description = binding.descriptionEditText.text.toString()
        )
    }
}