package com.example.core.di

import android.content.Context
import com.example.core.repositories.MockRepository
import com.example.navigation.AppNavigationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideAppNavigationProvider(
        @ApplicationContext context: Context
    ): AppNavigationProvider {
        return context as AppNavigationProvider
    }
}