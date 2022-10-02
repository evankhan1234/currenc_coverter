package com.currency.exchanger.data.rate.remote.repository

import android.util.Log
import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.rate.remote.api.ExchangeRateApi
import com.currency.exchanger.data.rate.remote.dto.RateResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.rate.Entity.InfoEntity
import com.currency.exchanger.domain.rate.Entity.QueryEntity
import com.currency.exchanger.domain.rate.Entity.RateEntity
import com.currency.exchanger.domain.rate.RateRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(private val exchangeRateApi: ExchangeRateApi) :
    RateRepository {
    override suspend fun convert(
        amount: Double,
        from: String,
        to: String
    ): Flow<BaseResult<RateEntity, RateResponse>> {
        return flow {
            val response = exchangeRateApi.convert(amount, from, to)
            if (response.isSuccessful) {
                val body = response.body()!!
                Log.e("ssss","Sss"+Gson().toJson(body))
                val info = InfoEntity(body.info.rate)
                val query = QueryEntity(
                    body.query.from,
                    body.query.to,
                    body.query.amount
                )
                val rateExchange = RateEntity(
                    body.success,
                    body.date,
                    body.result,
                    query,
                    info
                )

                emit(BaseResult.Success(rateExchange))
            } else {
//                val type = object : TypeToken<WrappedResponse<RateResponse>>() {}.type
//                val err = Gson().fromJson<WrappedResponse<RateResponse>>(
//                    response.errorBody()!!.charStream(), type
//                )!!
//                err.code = response.code()
//                emit(BaseResult.Error(err))
            }
        }
    }


}