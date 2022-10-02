package com.currency.exchanger.data.rate.remote

import com.currency.exchanger.data.common.module.DatabaseModule
import com.currency.exchanger.data.common.module.NetworkModule
import com.currency.exchanger.data.rate.local.RateLocalRepositoryImpl
import com.currency.exchanger.data.rate.local.dao.RateDao
import com.currency.exchanger.data.rate.local.datasource.RateLocalDataSource
import com.currency.exchanger.data.rate.local.datasource.RateLocalDataSourceImpl
import com.currency.exchanger.data.rate.remote.api.ExchangeRateApi
import com.currency.exchanger.data.rate.remote.repository.RateRepositoryImpl
import com.currency.exchanger.domain.rate.RateLocalRepository
import com.currency.exchanger.domain.rate.RateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class,DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class RateModule {
    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit) : ExchangeRateApi {
        return retrofit.create(ExchangeRateApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRateRepository(exchangeRateApi: ExchangeRateApi) : RateRepository {
        return RateRepositoryImpl(exchangeRateApi)
    }


    @Provides
    fun provideLocalDataSource(rateDao: RateDao): RateLocalDataSource {
        return RateLocalDataSourceImpl(rateDao)
    }

    @Singleton
    @Provides
    fun provideRateLocalRepository(rateLocalDataSource: RateLocalDataSource) : RateLocalRepository {
        return RateLocalRepositoryImpl(rateLocalDataSource)
    }
}