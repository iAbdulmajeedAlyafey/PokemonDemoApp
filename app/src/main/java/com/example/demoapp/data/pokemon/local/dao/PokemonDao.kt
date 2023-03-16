package com.example.demoapp.data.pokemon.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demoapp.data.pokemon.local.model.CachedPokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemon WHERE isFavorite = 1")
    fun getFavoritePokemonList(): Flow<List<CachedPokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePokemon(pokemon: CachedPokemon)

    @Delete
    suspend fun deleteFavoritePokemon(pokemon: CachedPokemon)
}
