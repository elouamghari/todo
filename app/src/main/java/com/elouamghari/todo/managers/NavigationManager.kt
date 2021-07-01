package com.elouamghari.todo.managers

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.elouamghari.todo.R
import java.lang.Exception

/**
 * Created by Mohamed Elouamghari on 17/06/2021
 */
object NavigationManager {

    private fun getNavController(activity: FragmentActivity): NavController{
        return Navigation.findNavController(activity, R.id.app_nav_host)
    }

    fun navigate(activity: FragmentActivity, action: Int, args: Bundle? = null){
        try{
            getNavController(activity).navigate(action, args)
        }
        catch (ex: Exception){
            // ignore
        }
    }

    fun popBackStack(activity: FragmentActivity){
        try{
            getNavController(activity).popBackStack()
        }
        catch (ex: Exception){
            // ignore
        }
    }

}