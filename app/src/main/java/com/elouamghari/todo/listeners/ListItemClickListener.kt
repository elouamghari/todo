package com.elouamghari.todo.listeners

fun interface ListItemClickListener<T : Any> {
    fun onItemClick(item: T)
}