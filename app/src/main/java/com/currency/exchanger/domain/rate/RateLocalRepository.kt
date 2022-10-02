package com.currency.exchanger.domain.rate

import android.graphics.Movie
import androidx.paging.PagingData
import com.currency.exchanger.data.rate.local.dto.Rate
import kotlinx.coroutines.flow.Flow

interface RateLocalRepository {
    suspend fun addRates(rate: Rate)
}