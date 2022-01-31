package com.siemens.leadx.ui.profile

import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.apicalls.AuthenticationApiCalls
import com.siemens.leadx.utils.base.BaseRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val authenticationApiCalls: AuthenticationApiCalls) :
    BaseRepository() {

    fun logout(): Single<BaseResponse<Any>> {
        val fireBaseToken = localDataUtils.sharedPrefsUtils.getFireBaseToken()
        return (
                if (fireBaseToken?.token.isNullOrBlank() || fireBaseToken?.doNeedToRegisterTokenServer == true)
                    Single.just(BaseResponse())
                else authenticationApiCalls.deleteToken(
                    localDataUtils.getDeviceId(),
                    fireBaseToken?.token
                )
                )
            .doOnSuccess {
                clearUserData()
            }
    }
}
