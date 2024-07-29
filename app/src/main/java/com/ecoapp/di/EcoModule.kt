package com.ecoapp.di

import com.ecoapp.domain.repository.EcoRepository
import com.ecoapp.domain.repository.EcoRepositoryImpl
import com.ecoapp.domain.usecase.FetchEcoDataUseCase
import com.ecoapp.domain.usecase.FetchEcoDataUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object EcoModule {
    @Provides
    @Singleton
    fun provideEcoRepository(): EcoRepository {
        return EcoRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideFetchEcoDataUseCase(
        ecoRepository: EcoRepository
    ): FetchEcoDataUseCase {
        return FetchEcoDataUseCaseImpl(ecoRepository)
    }

}