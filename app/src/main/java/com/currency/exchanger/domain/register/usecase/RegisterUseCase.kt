package com.currency.exchanger.domain.register.usecase

import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.register.remote.dto.RegisterRequest
import com.currency.exchanger.data.register.remote.dto.RegisterResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.register.RegisterRepository
import com.currency.exchanger.domain.register.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    suspend fun invoke(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity, WrappedResponse<RegisterResponse>>> {
        return registerRepository.register(registerRequest)
    }
}