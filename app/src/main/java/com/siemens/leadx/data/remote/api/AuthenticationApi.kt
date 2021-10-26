package com.siemens.leadx.data.remote.api

import com.siemens.leadx.data.remote.ApiUrls
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.UserData
import com.siemens.leadx.data.remote.requests.FCMTokenRequest
import com.siemens.leadx.data.remote.requests.LoginRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthenticationApi {

    @POST(ApiUrls.LOGIN)
    fun login(@Body loginRequest: LoginRequest): Single<BaseResponse<UserData>>

    @PUT(ApiUrls.PUT_FIREBASE_TOKEN)
    fun addFirebaseToken(
        @Body fcmTokenRequest: FCMTokenRequest,
    ): Single<BaseResponse<Any>>

    @HTTP(method = "DELETE", path = ApiUrls.DELETE_FIREBASE_TOKEN, hasBody = true)
    fun deleteToken(@Body fcmTokenRequest: FCMTokenRequest): Single<BaseResponse<Any>>
}
