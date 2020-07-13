package com.example.weatherme.subviews

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.updateLayoutParams
import com.example.weatherme.R
import com.example.weatherme.extensions.onTextChanged
import com.example.weatherme.ui.MainActivity

class SearchEditText(context: Context): AppCompatEditText(context) {

    init {
        configureElement()
        configureListeners()
    }

    private fun configureListeners() {
        setOnFocusChangeListener { _, isChanged ->
            Log.d(TAG, "Is changed $isChanged")
            text = null
        }

    }

    private fun configureElement() {
        isSingleLine = true
        setBackgroundResource(R.color.colorPrimary)
        setSelection(0)
        layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    companion object {
        private val TAG = SearchEditText::class.simpleName
    }


}