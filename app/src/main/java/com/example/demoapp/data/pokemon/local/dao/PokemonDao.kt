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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoritePokemon(pokemon: CachedPokemon)

    @Query("SELECT * FROM Pokemon WHERE isFavorite = 1")
    fun getFavoritesList(): Flow<CachedPokemon>

    @Delete
    suspend fun deleteFavoritePokemon(pokemon: CachedPokemon)
}
