package com.example.demoapp.util

import com.google.gson.Gson

fun Any?.toGson(): String = Gson().toJson(this)