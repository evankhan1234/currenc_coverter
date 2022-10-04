package com.currency.exchanger.data.rate.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "balance")
data class Balance(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    @SerializedName("currency_name")
    val currencyName: String,
    @SerializedName("currency_symbol")
    val currency_symbol: String,
    @SerializedName("available")
    val available: Double?,
    @SerializedName("euro_available")
    val euroAvailable: Double?,
    @SerializedName("update_date")
    val updateDate: String?,
) : Serializable