package com.elouamghari.todo.core.adapters

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elouamghari.todo.application.MainApplication
import com.elouamghari.todo.listeners.CallbackListener

abstract class AnimatedListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val animateOnce: Boolean = true,
    private val animationOffset: Long = 75L,
    private val animationDuration: Long = 300L
) : ListAdapter<T, VH>(diffCallback) {

    private var lastAnimatedPosition: Int = -1
    private var lastAnimationTime: Long = 0L

    @AnimRes
    protected abstract fun getAnimationResId(): Int

    protected abstract fun onBindAnimatedViewHolder(holder: VH, position: Int)

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindAnimatedViewHolder(holder, position)
        runAnimation(holder, position)
    }

    private fun runAnimation(holder: VH, position: Int) {
        if (!animateOnce || (animateOnce && position > lastAnimatedPosition)){
            val animationController = createAnimation()
            val delay = calculateDelay(position)
            Log.d("anim----", "runAnimation: delay = $delay")
            animationController.startOffset = delay
            holder.itemView.startAnimationWithCallback(animationController, position) {index ->
                lastAnimatedPosition = index
                lastAnimationTime = System.currentTimeMillis()
            }
        }
    }

    private fun createAnimation(): Animation{
        return AnimationUtils.loadAnimation(MainApplication.instance.applicationContext, getAnimationResId())
            .apply {
                duration = animationDuration
            }
    }

    private fun calculateDelay(position: Int): Long{
        var positionDiff = position - lastAnimatedPosition
        positionDiff = if(positionDiff < 0)
            0
        else{
            positionDiff
        }
        return animationOffset * positionDiff
    }

    private fun View.startAnimationWithCallback(animation: Animation, position: Int, callback: CallbackListener<Int>? = null){
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                callback?.onCallback(position)
            }

            override fun onAnimationEnd(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        this.startAnimation(animation)
    }

}