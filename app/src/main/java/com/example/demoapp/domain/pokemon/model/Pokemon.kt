package com.example.demoapp.domain.pokemon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: String,
    val name: String,
    val artist: String,
    val healthPoints: Int,
    val image: String,
    val isFavorite: Boolean,
) : Parcelable