package com.example.demoapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson

fun Any?.toGson(): String = Gson().toJson(this)

fun ImageView.load(url: String?) = Glide.with(this)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)
