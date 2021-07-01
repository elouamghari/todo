package com.elouamghari.todo.ui.todos.list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elouamghari.todo.R
import com.elouamghari.todo.core.adapters.ReorderAnimatedListAdapter
import com.elouamghari.todo.database.entities.Todo
import com.elouamghari.todo.databinding.ListItemTodoBinding
import com.elouamghari.todo.listeners.OrderChangedListener
import com.elouamghari.todo.ui.todos.list.listeners.TodoCheckChangedListener
import com.elouamghari.todo.ui.todos.list.listeners.TodoEditListener


class TodosAdapter : ReorderAnimatedListAdapter<Todo, TodosAdapter.TodoViewHolder>(TodosComparator()) {

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemTodoBinding.bind(itemView).also {binding->
            binding.editTodoButton.setOnClickListener {
                val todo = getItem(adapterPosition)
                todoEditListener?.onTodoEdit(todo)
            }

            binding.todoCheckBox.setOnCheckedChangeListener { _, isChecked ->
                val todo = getItem(adapterPosition)
                if (todo.isAccomplished != isChecked){
                    todo.isAccomplished = isChecked
                    todoCheckChangedListener?.onItemCheckChanged(todo)
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        fun bind(todo: Todo){
            binding.todo = todo
        }
    }

    class TodosComparator : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private var todoEditListener: TodoEditListener? = null

    fun setTodoEditListener(todoEditListener: TodoEditListener){
        this.todoEditListener = todoEditListener
    }

    private var todoCheckChangedListener: TodoCheckChangedListener? = null

    fun setListItemCheckChangedListener(todoCheckChangedListener: TodoCheckChangedListener){
        this.todoCheckChangedListener = todoCheckChangedListener
    }

    private var orderChangedListener: OrderChangedListener<Todo>? = null

    fun setOrderChangedListener(orderChangedListener: OrderChangedListener<Todo>){
        this.orderChangedListener = orderChangedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_todo, parent, false))
    }

    override fun onBindAnimatedViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getAnimationResId(): Int {
        return R.anim.right_translation_animation
    }

    override fun orderChanged(from: Int, to: Int) {
        super.orderChanged(from, to)
        items?.let {items->
            items.reorderTodos()
            orderChangedListener?.orderChanged(items)
        }
    }

    private fun List<Todo>.reorderTodos(){
        forEachIndexed { index, todo ->
            todo.order = (index+1)
        }
    }

}