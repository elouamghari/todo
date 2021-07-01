package com.elouamghari.todo.ui.classifications.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elouamghari.todo.R
import com.elouamghari.todo.application.MainApplication
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.databinding.FragmentClassificationsBinding
import com.elouamghari.todo.managers.NavigationManager
import com.elouamghari.todo.ui.classifications.add.AddClassificationBottomSheet
import com.elouamghari.todo.ui.classifications.list.constants.ClassificationArgumentsKeys
import com.elouamghari.todo.ui.classifications.list.adapters.ClassificationsAdapter
import com.elouamghari.todo.ui.classifications.list.viewmodels.ClassificationViewModel
import com.elouamghari.todo.ui.classifications.list.viewmodels.factories.ClassificationViewModelFactory


class ClassificationsFragment : Fragment() {

    private lateinit var binding: FragmentClassificationsBinding
    private val viewModel : ClassificationViewModel by viewModels{
        ClassificationViewModelFactory(MainApplication.instance.classificationRepository)
    }
    private val adapter: ClassificationsAdapter = ClassificationsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentClassificationsBinding.inflate(inflater)
        binding.classificationsRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.classifications.observe(viewLifecycleOwner, this::observeClassifications)

        adapter.setListItemClickListener{item ->
            item.id?.let {
                val args = Bundle()
                args.putInt(ClassificationArgumentsKeys.CLASSIFICATION_ID, it)
                NavigationManager.navigate(requireActivity(), R.id.action_classificationsFragment_to_todosFragment, args)
            }
        }

        binding.addClassificationButton.setOnClickListener {
            createClassification()
        }
    }

    private fun observeClassifications(classifications: List<Classification>?) {
        classifications?.let{
            adapter.submitList(classifications)
        }
    }

    private fun createClassification(){
        val addClassification = AddClassificationBottomSheet()
        addClassification.show(parentFragmentManager)
    }
}