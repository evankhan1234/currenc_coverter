package com.currency.exchanger.data.rate.local.dao


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currency.exchanger.data.rate.local.dto.Balance
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBalances(movies: List<Balance>)

    @Query("SELECT * FROM balance")
    fun getAllBalances(): PagingSource<Int, Balance>

    @Query("SELECT * FROM balance WHERE pk = :id")
    fun getBalance(id: Int): Flow<Balance>

    @Query("DELETE FROM balance")
    suspend fun deleteAllBalances()

    @Insert
    fun insertAll(vararg balance: Balance?)
}