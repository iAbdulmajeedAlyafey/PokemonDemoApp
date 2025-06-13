package com.example.demoapp.di

import com.example.demoapp.data.pokemon.repository.FakePokemonRepositoryImpl
import com.example.demoapp.data.pokemon.repository.PokemonRepositoryImpl
import com.example.demoapp.domain.pokemon.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindPokemonRepository(repository: PokemonRepositoryImpl): PokemonRepository

    @FakeRepository
    @Binds
    @Singleton
    fun bindFakePokemonRepository(repository: FakePokemonRepositoryImpl): PokemonRepository
}