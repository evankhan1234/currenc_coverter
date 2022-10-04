package com.currency.exchanger.data.rate.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Exchange
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface ExchangeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExchange(exchange: Exchange)

    @Query("SELECT * FROM exchange")
    fun getAllExchanges(): Flow<List<Exchange>>

    @Query("SELECT * FROM exchange WHERE fromCurrency = :movieId")
    fun getRate(movieId: Int): Flow<Exchange>

    @Query("DELETE FROM exchange")
    suspend fun deleteAllRates()

    @Query("SELECT COUNT(*) FROM exchange ")
    suspend fun exchangeCount (): Int

}