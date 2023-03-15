package com.example.demoapp.data.pokemon.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.demoapp.domain.pokemon.model.Pokemon

@Entity(tableName = "Pokemon")
data class CachedPokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val artist: String,
    val healthPoints: Int,
    val isFavorite: Boolean,
    val image: String,
)

fun CachedPokemon.asPokemon() = Pokemon(
    id = id,
    name = name,
    artist = artist,
    healthPoints = healthPoints,
    image = image,
    isFavorite = isFavorite,
)