package com.elouamghari.todo.core.adapters

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elouamghari.todo.managers.ReorderListManager
import java.util.*

abstract class ReorderAnimatedListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    animateOnce: Boolean = true,
    animationOffset: Long = 75L,
    animationDuration: Long = 300L
) : AnimatedListAdapter<T,VH>(diffCallback, animateOnce, animationOffset, animationDuration){

    protected var items: List<T>? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.enableReorderList()
    }

    override fun submitList(list: List<T>?) {
        this.items = list
        super.submitList(list)
    }

    override fun submitList(list: List<T>?, commitCallback: Runnable?) {
        this.items = list
        super.submitList(list, commitCallback)
    }

    private fun swapItems(from: Int, to: Int){
        Collections.swap(items, from, to)
        notifyItemMoved(from, to)
    }

    @CallSuper
    open fun orderChanged(from: Int, to: Int) {
        swapItems(from, to)
    }

    private fun RecyclerView.enableReorderList(){
        ReorderListManager.attachToRecyclerView(this)
    }

}