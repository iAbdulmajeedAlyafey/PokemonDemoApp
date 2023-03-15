package com.example.demoapp.util

fun Int?.orDefault(defaultValue: Int = 0): Int = this ?: defaultValue

fun Int?.toFlotOrDefault(defaultValue: Float = 0f): Float = this?.toFloat() ?: defaultValue

fun Long?.orDefault(defaultValue: Long = 0L): Long = this ?: defaultValue

fun Float?.orDefault(defaultValue: Float = 0F): Float = this ?: defaultValue

fun Float?.toDoubleOrDefault(defaultValue: Double = 0.0): Double = this?.toDouble() ?: defaultValue

fun Boolean?.orDefault(defaultValue: Boolean = false): Boolean = this ?: defaultValue

fun Double?.orDefault(defaultValue: Double = 0.0): Double = this ?: defaultValue