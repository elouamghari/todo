package com.elouamghari.todo.ui.classifications.list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elouamghari.todo.R
import com.elouamghari.todo.core.adapters.AnimatedListAdapter
import com.elouamghari.todo.core.adapters.ReorderAnimatedListAdapter
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.databinding.ListItemClassificationBinding
import com.elouamghari.todo.listeners.ListItemClickListener

class ClassificationsAdapter : AnimatedListAdapter<Classification, ClassificationsAdapter.ClassificationViewHolder>(ClassificationsComparator(), animationOffset = 100L) {

    inner class ClassificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ListItemClassificationBinding.bind(itemView)

        init {
            binding.root.setOnClickListener {
                listItemClickListener?.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(classification: Classification){
            binding.classification = classification
        }
    }

    class ClassificationsComparator : DiffUtil.ItemCallback<Classification>() {
        override fun areItemsTheSame(oldItem: Classification, newItem: Classification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Classification, newItem: Classification): Boolean {
            return oldItem == newItem
        }
    }

    private var listItemClickListener: ListItemClickListener<Classification>? = null

    fun setListItemClickListener(listItemClickListener: ListItemClickListener<Classification>){
        this.listItemClickListener = listItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassificationViewHolder {
        return ClassificationViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_classification, parent, false))
    }

    override fun onBindAnimatedViewHolder(holder: ClassificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getAnimationResId(): Int {
        return R.anim.fade_scale_animation
    }
}