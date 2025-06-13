package com.example.demoapp.data.pokemon.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemon")
data class CachedPokemon(
    @PrimaryKey val id: String,
    val name: String,
    val artist: String,
    val healthPoints: Int,
    val isFavorite: Boolean,
    val image: String,
)