package com.currency.exchanger.data.rate.local.datasource

import android.graphics.Movie
import com.currency.exchanger.data.rate.local.dao.RateDao
import com.currency.exchanger.data.rate.local.dto.Rate
import kotlinx.coroutines.flow.Flow

class RateLocalDataSourceImpl (private val rateDao: RateDao) : RateLocalDataSource {
    override suspend fun addRates(rate: Rate) {
        rateDao.addRates(rate)
    }
}