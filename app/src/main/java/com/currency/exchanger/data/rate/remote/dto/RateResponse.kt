package com.currency.exchanger.data.rate.remote.dto

import com.currency.exchanger.data.product.remote.dto.ProductUserResponse
import com.google.gson.annotations.SerializedName

data class RateResponse(
    @SerializedName("success") var success: Boolean,
    @SerializedName("product_name") var name: String,
    @SerializedName("price") var price: Int,
    @SerializedName("user") var user: ProductUserResponse
)