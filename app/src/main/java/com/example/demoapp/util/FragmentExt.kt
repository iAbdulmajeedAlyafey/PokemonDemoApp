package com.example.demoapp.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(
    text: String,
    duration: Int = Toast.LENGTH_SHORT,
) = Toast.makeText(context, text, duration).show()

fun Fragment.showError(error: String) {
    // Show error dialog.
    toast(error)
}