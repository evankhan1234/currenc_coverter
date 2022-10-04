package com.currency.exchanger.data.rate.remote

import com.currency.exchanger.data.common.module.DatabaseModule
import com.currency.exchanger.data.common.module.NetworkModule
import com.currency.exchanger.data.rate.local.BalanceLocalRepositoryImpl
import com.currency.exchanger.data.rate.local.ExchangeLocalRepositoryImpl
import com.currency.exchanger.data.rate.local.dao.BalanceDao
import com.currency.exchanger.data.rate.local.dao.ExchangeDao
import com.currency.exchanger.data.rate.local.datasource.BalanceLocalDataSource
import com.currency.exchanger.data.rate.local.datasource.BalanceLocalDataSourceImpl
import com.currency.exchanger.data.rate.local.datasource.ExchangeLocalDataSource
import com.currency.exchanger.data.rate.local.datasource.ExchangeLocalDataSourceImpl
import com.currency.exchanger.data.rate.remote.api.ExchangeRateApi
import com.currency.exchanger.data.rate.remote.repository.RateRepositoryImpl
import com.currency.exchanger.domain.rate.BalanceLocalRepository
import com.currency.exchanger.domain.rate.ExchangeLocalRepository
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
    fun provideLocalDataSource(rateDao: ExchangeDao): ExchangeLocalDataSource {
        return ExchangeLocalDataSourceImpl(rateDao)
    }

    @Singleton
    @Provides
    fun provideRateLocalRepository(exchangeLocalDataSource: ExchangeLocalDataSource) : ExchangeLocalRepository {
        return ExchangeLocalRepositoryImpl(exchangeLocalDataSource)
    }

    @Provides
    fun provideBalanceLocalDataSource(balanceDao: BalanceDao): BalanceLocalDataSource {
        return BalanceLocalDataSourceImpl(balanceDao)
    }

    @Singleton
    @Provides
    fun provideBalanceLocalRepository(balanceLocalDataSource: BalanceLocalDataSource) : BalanceLocalRepository {
        return BalanceLocalRepositoryImpl(balanceLocalDataSource)
    }
}