package com.currency.exchanger.data.rate.local

import android.graphics.Movie
import androidx.paging.PagingData
import com.currency.exchanger.data.rate.local.datasource.RateLocalDataSource
import com.currency.exchanger.data.rate.local.dto.Rate
import com.currency.exchanger.domain.rate.RateLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RateLocalRepositoryImpl  @Inject constructor(
    private val rateLocalDataSource: RateLocalDataSource,
) :
    RateLocalRepository {


    override suspend fun addRates(rate: Rate) {
        rateLocalDataSource.addRates(rate)
    }
}