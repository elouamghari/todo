package com.elouamghari.todo.listeners

fun interface OrderChangedListener<T> {
    fun orderChanged(items: List<T>)
}