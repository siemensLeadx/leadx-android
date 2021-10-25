package com.siemens.leadx.ui.profile

import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.apicalls.AuthenticationApiCalls
import com.siemens.leadx.utils.base.BaseRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val authenticationApiCalls: AuthenticationApiCalls) :
    BaseRepository() {

    fun logout() = // authenticationApiCalls.deleteToken(localDataUtils.getDeviceId())
        Single.just(BaseResponse<Any>())
            .doOnSuccess {
                clearUserData()
            }
}
