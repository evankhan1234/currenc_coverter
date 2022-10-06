package com.currency.exchanger.data.currency.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.currency.exchanger.data.currency.local.dao.BalanceDao
import com.currency.exchanger.data.currency.local.dao.ExchangeDao
import com.currency.exchanger.data.currency.local.dto.Balance
import com.currency.exchanger.data.currency.local.dto.Exchange

@Database(
    entities = [Exchange::class, Balance::class],
    version = 4,
    exportSchema = false
)
abstract class CurrencyDB : RoomDatabase() {
    abstract fun exchangeDao(): ExchangeDao
    abstract fun balanceDao(): BalanceDao
}