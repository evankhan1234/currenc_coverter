package com.currency.exchanger.data.rate.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.currency.exchanger.data.rate.local.dao.BalanceDao
import com.currency.exchanger.data.rate.local.dao.ExchangeDao
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Exchange

@Database(
    entities = [Exchange::class, Balance::class],
    version = 4,
    exportSchema = false
)
abstract class CurrencyDB : RoomDatabase() {
    abstract fun rateDao(): ExchangeDao
    abstract fun balanceDao(): BalanceDao
}