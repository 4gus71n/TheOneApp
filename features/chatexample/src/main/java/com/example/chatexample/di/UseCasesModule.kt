package com.example.chatexample.di

import com.example.core.repositories.MockRepository
import com.example.chatexample.usecase.FetchMessagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCasesModule {
    @Singleton
    @Provides
    fun provideFetchMessagesUseCase(mockRepository: MockRepository): FetchMessagesUseCase {
        return FetchMessagesUseCase(mockRepository)
    }
}