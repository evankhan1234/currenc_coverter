package com.currency.exchanger.domain.login

import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.login.remote.dto.LoginRequest
import com.currency.exchanger.data.login.remote.dto.LoginResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.login.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>>
}