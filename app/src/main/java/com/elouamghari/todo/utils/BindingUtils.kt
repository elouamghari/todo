package com.elouamghari.todo.utils

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.elouamghari.todo.database.entities.Todo
import com.google.android.material.checkbox.MaterialCheckBox

@BindingAdapter("todoName")
fun MaterialCheckBox.setTodoName(todo: Todo) {
    text = if (todo.isAccomplished){
        val spannable = SpannableString(todo.name)
        spannable.setSpan(StrikethroughSpan(), 0, todo.name.length, 0)
        spannable
    }
    else{
        todo.name
    }
}

@BindingAdapter("todoDescription")
fun TextView.setTodoDescription(todo: Todo) {
    text = if (todo.isAccomplished){
        val spannable = SpannableString(todo.description)
        spannable.setSpan(StrikethroughSpan(), 0, todo.description.length, 0)
        spannable
    }
    else{
        todo.description
    }
}