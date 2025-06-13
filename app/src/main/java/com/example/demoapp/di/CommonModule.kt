package com.example.demoapp.di

import com.example.demoapp.util.EncryptionUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {

    @Singleton
    @Provides
    fun provideEncryptionUtil(): EncryptionUtils = EncryptionUtils()
}