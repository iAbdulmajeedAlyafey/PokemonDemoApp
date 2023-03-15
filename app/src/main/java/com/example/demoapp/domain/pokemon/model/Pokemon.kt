package com.example.demoapp.domain.pokemon.model

import android.os.Parcelable
import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val artist: String,
    val healthPoints: Int,
    val image: String,
    val isFavorite: Boolean,
) : Parcelable

fun Pokemon.asCachedPokemon() = CachedPokemon(
    id = id,
    name = name,
    artist = artist,
    healthPoints = healthPoints,
    image = image,
    isFavorite = isFavorite
)