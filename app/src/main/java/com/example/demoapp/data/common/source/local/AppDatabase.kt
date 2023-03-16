package com.example.demoapp.data.common.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demoapp.data.pokemon.local.dao.PokemonDao
import com.example.demoapp.data.pokemon.local.model.CachedPokemon

@Database(
    entities = [CachedPokemon::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}