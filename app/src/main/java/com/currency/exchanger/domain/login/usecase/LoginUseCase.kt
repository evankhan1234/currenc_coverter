package com.currency.exchanger.domain.login.usecase

import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.login.remote.dto.LoginRequest
import com.currency.exchanger.data.login.remote.dto.LoginResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.login.LoginRepository
import com.currency.exchanger.domain.login.entity.LoginEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return loginRepository.login(loginRequest)
    }

}