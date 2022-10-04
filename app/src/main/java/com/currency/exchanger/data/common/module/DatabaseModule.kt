package com.currency.exchanger.data.common.module

import android.app.Application
import androidx.room.Room
import com.currency.exchanger.data.rate.local.dao.BalanceDao
import com.currency.exchanger.data.rate.local.dao.ExchangeDao
import com.currency.exchanger.data.rate.local.db.CurrencyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(app: Application): CurrencyDB =
        Room.databaseBuilder(app, CurrencyDB::class.java, "currency_db").allowMainThreadQueries().fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRateDao(currencyDB: CurrencyDB) : ExchangeDao = currencyDB.rateDao()

    @Provides
    fun provideBalanceDao(currencyDB: CurrencyDB) : BalanceDao = currencyDB.balanceDao()
}