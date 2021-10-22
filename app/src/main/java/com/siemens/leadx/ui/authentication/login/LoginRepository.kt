package com.siemens.leadx.ui.authentication.login

import com.siemens.leadx.data.remote.apicalls.AuthenticationApiCalls
import com.siemens.leadx.data.remote.requests.LoginRequest
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class LoginRepository @Inject constructor(private val authenticationApiCalls: AuthenticationApiCalls) :
    BaseRepository() {

    fun login(email: String, firstName: String, lastName: String, id: String) =
//        authenticationApiCalls.login(
//            LoginRequest(id, lastName, firstName, email)).doOnSuccess {
//            saveUser(it.data)
//        }
        authenticationApiCalls.login(
            LoginRequest(
                "123456787",
                "sobhy",
                "mohammed",
                "mohammed.sobhy@siemens-healthineers.com"
            )
        )
            .doOnSuccess {
                saveUser(it.data)
            }
}
