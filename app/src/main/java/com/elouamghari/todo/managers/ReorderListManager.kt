package com.elouamghari.todo.managers

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elouamghari.todo.core.adapters.ReorderAnimatedListAdapter

object ReorderListManager {

    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.START or ItemTouchHelper.END
            , 0){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                if (recyclerView.adapter is ReorderAnimatedListAdapter<*, *>){
                    val adapter: ReorderAnimatedListAdapter<*, *> = recyclerView.adapter as ReorderAnimatedListAdapter<*, *>
                    adapter.orderChanged(viewHolder.adapterPosition, target.adapterPosition)
                    return true
                }
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        })
    }

    fun attachToRecyclerView(recyclerView: RecyclerView){
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}