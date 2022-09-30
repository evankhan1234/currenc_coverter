package com.currency.exchanger.domain.product.usecase

import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.product.remote.dto.ProductResponse
import com.currency.exchanger.data.product.remote.dto.ProductUpdateRequest
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.product.ProductRepository
import com.currency.exchanger.domain.product.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val productRepository: ProductRepository){
    suspend fun invoke(productUpdateRequest: ProductUpdateRequest, id: String) : Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>> {
        return productRepository.updateProduct(productUpdateRequest, id)
    }
}