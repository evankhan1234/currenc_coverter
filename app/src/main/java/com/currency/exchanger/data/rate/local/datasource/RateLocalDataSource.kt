package com.currency.exchanger.data.rate.local.datasource

import android.graphics.Movie
import com.currency.exchanger.data.rate.local.dto.Rate
import kotlinx.coroutines.flow.Flow

interface RateLocalDataSource {
    suspend fun addRates(rate: Rate)
}