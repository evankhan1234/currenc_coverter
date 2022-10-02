package com.currency.exchanger.data.rate.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.currency.exchanger.data.rate.local.dao.BalanceDao
import com.currency.exchanger.data.rate.local.dao.RateDao
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Rate

@Database(
    entities = [Rate::class, Balance::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDB : RoomDatabase() {
    abstract fun rateDao(): RateDao
    abstract fun balanceDao(): BalanceDao
}