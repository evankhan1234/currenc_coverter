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
    suspend fun addBalances(balance: Balance)

    @Query("SELECT * FROM balance order by pk desc")
    fun getAllBalances(): Flow<List<Balance>>

    @Query("SELECT * FROM balance WHERE pk = :id")
    fun getBalance(id: Int): Flow<Balance>

    @Query("DELETE FROM balance")
    suspend fun deleteAllBalances()

    @Query("SELECT COUNT(*) FROM balance")
    suspend fun balanceSize ():Int

    @Insert
    fun insertAll(vararg balance: Balance?)
}