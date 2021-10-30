package com.siemens.leadx.data.remote.api

import com.siemens.leadx.data.remote.ApiUrls
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.RemoteKeys
import com.siemens.leadx.data.remote.entites.Notification
import com.siemens.leadx.data.remote.entites.UserData
import com.siemens.leadx.data.remote.requests.FCMTokenRequest
import com.siemens.leadx.data.remote.requests.LoginRequest
import com.siemens.leadx.utils.Constants
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthenticationApi {

    @POST(ApiUrls.LOGIN)
    fun login(@Body loginRequest: LoginRequest): Single<BaseResponse<UserData>>

    @PUT(ApiUrls.PUT_FIREBASE_TOKEN)
    fun addFirebaseToken(
        @Body fcmTokenRequest: FCMTokenRequest,
    ): Single<BaseResponse<Any>>

    @HTTP(method = "DELETE", path = ApiUrls.DELETE_FIREBASE_TOKEN, hasBody = true)
    fun deleteToken(@Body fcmTokenRequest: FCMTokenRequest): Single<BaseResponse<Any>>

    @GET(ApiUrls.GET_NOTIFICATIONS)
    fun executeGetNotifications(
        @Query(RemoteKeys.PAGE) page: Int,
        @Query(RemoteKeys.PER_PAGE) perPage: Int = Constants.PAGE_SIZE,
    ): Single<BaseResponse<List<Notification>>>
}
