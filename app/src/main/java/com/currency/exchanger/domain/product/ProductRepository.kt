package com.currency.exchanger.domain.product

import com.currency.exchanger.data.common.utils.WrappedListResponse
import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.product.remote.dto.ProductCreateRequest
import com.currency.exchanger.data.product.remote.dto.ProductResponse
import com.currency.exchanger.data.product.remote.dto.ProductUpdateRequest
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.product.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllMyProducts() : Flow<BaseResult<List<ProductEntity>, WrappedListResponse<ProductResponse>>>
    suspend fun getProductById(id: String) : Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>>
    suspend fun updateProduct(productUpdateRequest: ProductUpdateRequest, id: String) : Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>>
    suspend fun deleteProductById(id: String) : Flow<BaseResult<Unit, WrappedResponse<ProductResponse>>>
    suspend fun createProduct(productCreateRequest: ProductCreateRequest) : Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>>
}