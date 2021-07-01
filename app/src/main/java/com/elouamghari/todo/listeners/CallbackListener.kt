package com.elouamghari.todo.listeners

fun interface CallbackListener<T : Any> {
    fun onCallback(item: T)
}