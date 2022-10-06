package com.currency.exchanger.data.currency.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currency.exchanger.data.currency.local.dto.Exchange
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExchange(exchange: Exchange)

    @Query("SELECT * FROM exchange order by pk desc")
    fun getAllExchanges(): Flow<List<Exchange>>

    @Query("SELECT * FROM exchange WHERE fromCurrency = :movieId")
    fun getRate(movieId: Int): Flow<Exchange>

    @Query("DELETE FROM exchange")
    suspend fun deleteAllRates()

    @Query("SELECT COUNT(*) FROM exchange ")
    suspend fun exchangeCount (): Int

}