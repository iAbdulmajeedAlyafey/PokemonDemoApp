package com.example.demoapp.di

import android.content.Context
import androidx.room.Room
import com.example.demoapp.data.common.source.local.AppDatabase
import com.example.demoapp.data.pokemon.local.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database",
        ).build()

    @Provides
    fun providesPokemonDao(database: AppDatabase): PokemonDao = database.pokemonDao()

}
