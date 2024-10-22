package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.di

import android.content.Context
import androidx.room.Room
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.repositoryImpl.PrayerTimesRepositoryImpl
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.repositoryImpl.QiblaRepositoryImpl
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local.PrayerTimeDataBase
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.local.dao.PrayerTimeDao
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.api.PrayerTimesApiService
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.data.source.remote.api.QiblaApiService
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository.PrayerTimesRepository
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.repository.QiblaRepository
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.usecase.GetCalendarPrayerTimesUseCase
import com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.usecase.GetQiblaDirectionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AladhanModule {

    @Provides
    @Singleton
    fun providePrayerTimesApiService(retrofit: Retrofit): PrayerTimesApiService {
        return retrofit.create(PrayerTimesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQiblaApiService(retrofit: Retrofit): QiblaApiService {
        return retrofit.create(QiblaApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePrayerTimesRepository(
         prayerTimeDao: PrayerTimeDao,
         prayerTimesApiService: PrayerTimesApiService
    ): PrayerTimesRepository {
        return PrayerTimesRepositoryImpl(prayerTimeDao, prayerTimesApiService)
    }

    @Provides
    @Singleton
    fun provideQiblaRepository( qiblaApiService: QiblaApiService): QiblaRepository {
        return QiblaRepositoryImpl(qiblaApiService)
    }

    @Provides
    @Singleton
    fun provideGetCalendarPrayerTimesUseCase(repository: PrayerTimesRepository): GetCalendarPrayerTimesUseCase {
        return GetCalendarPrayerTimesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetQiblaDirectionUseCase(repository: QiblaRepository): GetQiblaDirectionUseCase {
        return GetQiblaDirectionUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePrayerTimeDataBase(@ApplicationContext context: Context): PrayerTimeDataBase {
        return Room.databaseBuilder(context, PrayerTimeDataBase::class.java, "prayer_time_dataBase")
            .build()
    }

    @Provides
    @Singleton
    fun providePrayerTimeDao( database: PrayerTimeDataBase): PrayerTimeDao {
        return database.PrayerTimeDao()
    }
}