package com.example.weatherme.extensions

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.weatherme.R

fun FragmentManager.replace(fragment: Fragment, containerId: Int =  R.id.fragmentContainerView, addToBackStack: Boolean = true) {
    beginTransaction().apply {
        replace(containerId, fragment)
        if(addToBackStack) {
            addToBackStack(null)
        }
        commit()
    }
}