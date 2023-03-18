package com.example.demoapp.util

import android.content.Context
import androidx.annotation.RawRes

fun Context.readFile(@RawRes resId: Int): String = resources.openRawResource(resId)
    .bufferedReader()
    .use { it.readText() }