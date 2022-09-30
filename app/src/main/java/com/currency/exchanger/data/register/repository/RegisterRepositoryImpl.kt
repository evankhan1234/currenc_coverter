package com.currency.exchanger.data.register.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.register.remote.api.RegisterApi
import com.currency.exchanger.data.register.remote.dto.RegisterRequest
import com.currency.exchanger.data.register.remote.dto.RegisterResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.register.RegisterRepository
import com.currency.exchanger.domain.register.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerApi: RegisterApi) : RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, WrappedResponse<RegisterResponse>>> {
        return flow {
            val response = registerApi.register(registerRequest)
            if (response.isSuccessful){
                val body = response.body()!!
                val registerEntity = RegisterEntity(body.data?.id!!, body.data?.name!!, body.data?.email!!, body.data?.token!!)
                emit(BaseResult.Success(registerEntity))
            }else{
                val type = object : TypeToken<WrappedResponse<RegisterResponse>>(){}.type
                val err : WrappedResponse<RegisterResponse> = Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}