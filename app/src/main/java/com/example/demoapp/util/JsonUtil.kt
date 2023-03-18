package com.example.demoapp.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun <T> String?.toObject(classOfT: Class<T>): T? = Gson().fromJson(this, classOfT)

fun <T> String?.toObjectOrThrow(classOfT: Class<T>): T =
    toObject(classOfT)
        ?: throw JsonSyntaxException("Error in converting json to object!")

fun <T> String?.toListOfObject(classOfT: Class<T>?): List<T>? {
    val typeOfT: Type = TypeToken.getParameterized(MutableList::class.java, classOfT).type
    return Gson().fromJson(this, typeOfT)
}

fun <T> String?.toListOfObjectOrThrow(classOfT: Class<T>?): List<T> =
    toListOfObject(classOfT)
        ?: throw JsonSyntaxException("Error in converting json to object!")