package com.diegomalone.githubviewer.util.extension

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager

inline fun View.snack(messageResourceId: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit = {}) {
    this.snack(context.getString(messageResourceId), length, f)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit = {}) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(actionLabelResourceId: Int, color: Int? = null, listener: (View) -> Unit) {
    val actionLabel = context.getString(actionLabelResourceId)
    setAction(actionLabel, listener)
    color?.let { setActionTextColor(it) }
}

fun View.hideSoftKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.visible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}