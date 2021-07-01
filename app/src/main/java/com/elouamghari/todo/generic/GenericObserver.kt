package com.elouamghari.todo.generic

import androidx.lifecycle.Observer

abstract class GenericObserver<T> : Observer<T> {

    abstract fun onDataChanged(t: T)

    private var enabled: Boolean = true

    override fun onChanged(t: T) {
        if (enabled){
            onDataChanged(t)
        }
        else{
            enable()
        }
    }

    private fun enable(){
        enabled = true
    }

    fun disableObserverForNext(){
        enabled = false
    }
}