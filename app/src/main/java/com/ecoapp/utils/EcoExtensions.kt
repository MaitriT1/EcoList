package com.ecoapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView


fun Context.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val activity = this as? Activity
    activity?.currentFocus?.windowToken?.let {
        inputMethodManager.hideSoftInputFromWindow(it, 0)
    }
}

fun SearchView.reset() {
    setQuery("", false)
    clearFocus()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

