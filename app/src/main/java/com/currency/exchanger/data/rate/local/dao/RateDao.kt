package com.currency.exchanger.data.rate.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currency.exchanger.data.rate.local.dto.Rate
import kotlinx.coroutines.flow.Flow
@Dao
interface RateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRates(rate: Rate)

    @Query("SELECT * FROM rate")
    fun getAllRates(): PagingSource<Int, Rate>

    @Query("SELECT * FROM rate WHERE fromCurrency = :movieId")
    fun getRate(movieId: Int): Flow<Rate>

    @Query("DELETE FROM rate")
    suspend fun deleteAllRates()

}