package com.currency.exchanger.data.rate.remote.api

import com.currency.exchanger.data.common.utils.WrappedListResponse
import com.currency.exchanger.data.product.remote.dto.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("product/")
    suspend fun convert(@Query("amount") from: Double,
                        @Query("from") to: String,
                        @Query("to") claim: String,
                       ) : Response<WrappedListResponse<ProductResponse>>
}