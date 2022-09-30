package com.currency.exchanger.domain.product.usecase

import com.currency.exchanger.data.common.utils.WrappedListResponse
import com.currency.exchanger.data.product.remote.dto.ProductResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.product.ProductRepository
import com.currency.exchanger.domain.product.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun invoke() : Flow<BaseResult<List<ProductEntity>, WrappedListResponse<ProductResponse>>> {
        return productRepository.getAllMyProducts()
    }
}