package com.siemens.leadx.data.remote.apicalls

import com.siemens.leadx.data.remote.api.AuthenticationApi
import com.siemens.leadx.data.remote.requests.FCMTokenRequest
import com.siemens.leadx.data.remote.requests.LoginRequest
import retrofit2.Retrofit
import javax.inject.Inject

class AuthenticationApiCalls @Inject constructor(retrofit: Retrofit) {

    private val authApi = retrofit.create(AuthenticationApi::class.java)

    fun login(loginRequest: LoginRequest) = authApi.login(loginRequest)

    fun addFirebaseToken(model: String, token: String) =
        authApi.addFirebaseToken(FCMTokenRequest(model, token))

    fun deleteToken(model: String, token: String?) =
        authApi.deleteToken(FCMTokenRequest(model, token))

    fun getNotifications(
        page: Int,
    ) = authApi.executeGetNotifications(page)
}
