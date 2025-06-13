package com.example.demoapp.di

import com.example.demoapp.data.pokemon.remote.api.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiServicesModule {

    @Singleton
    @Provides
    fun providePokemonApiService(
        @MainClient retrofit : Retrofit,
    ) : PokemonApiService = retrofit.create(PokemonApiService::class.java)
}