package com.siemens.leadx.ui.authentication.login

import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.UserData
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel(loginRepository) {

    val status = SingleLiveEvent<Status<BaseResponse<UserData>, Any>>()

    fun doLogin(email: String, firstName: String, lastName: String, id: String) {
        subscribe(loginRepository.login(email, firstName, lastName, id), status)
    }
}
