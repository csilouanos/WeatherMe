package com.example.weatherme.ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class ViewBindingFragment<T : ViewBinding> : Fragment() {

    var _binding: T? = null

    val binding: T
        get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}