package com.anasmohammed.qiblatalmuslim.f00Gateways.google.di

import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.repositoryImpl.GoogleGeocodeRepositoryImpl
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.data.source.remote.api.GoogleGeocodeApiService
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.repository.GoogleGeocodeRepository
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.domain.usecase.GetAddressFromLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleModule {

    @Provides
    @Singleton
    fun provideGoogleApiService(retrofit: Retrofit): GoogleGeocodeApiService {
        return retrofit.create(GoogleGeocodeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGoogleRepository(apiService: GoogleGeocodeApiService): GoogleGeocodeRepository {
        return GoogleGeocodeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetAddressFromLocationUseCase(repository: GoogleGeocodeRepository): GetAddressFromLocationUseCase {
        return GetAddressFromLocationUseCase(repository)
    }
}
