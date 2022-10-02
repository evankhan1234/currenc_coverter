package com.currency.exchanger.data.rate.remote.api

import com.currency.exchanger.data.common.utils.WrappedListResponse
import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.product.remote.dto.ProductResponse
import com.currency.exchanger.data.rate.remote.dto.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("convert/")
    suspend fun convert(@Query("amount") from: Double,
                        @Query("from") to: String,
                        @Query("to") claim: String,
                       ) : Response<RateResponse>
}