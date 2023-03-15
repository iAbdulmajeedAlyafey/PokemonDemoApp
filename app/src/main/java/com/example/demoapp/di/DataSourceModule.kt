package com.example.demoapp.di

import com.example.demoapp.data.pokemon.local.source.PokemonLocalSource
import com.example.demoapp.data.pokemon.local.source.PokemonLocalSourceImpl
import com.example.demoapp.data.pokemon.remote.source.PokemonRemoteSource
import com.example.demoapp.data.pokemon.remote.source.PokemonRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindPokemonRemoteSource(source: PokemonRemoteSourceImpl): PokemonRemoteSource

    @Binds
    @Singleton
    fun bindPokemonLocalSource(source: PokemonLocalSourceImpl): PokemonLocalSource
}