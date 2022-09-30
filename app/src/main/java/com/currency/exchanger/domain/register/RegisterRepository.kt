package com.currency.exchanger.domain.register

import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.register.remote.dto.RegisterRequest
import com.currency.exchanger.data.register.remote.dto.RegisterResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.register.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity, WrappedResponse<RegisterResponse>>>
}