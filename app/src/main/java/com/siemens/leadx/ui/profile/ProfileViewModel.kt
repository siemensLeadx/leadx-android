package com.siemens.leadx.ui.profile

import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    BaseViewModel(profileRepository) {

    val logoutStatus = SingleLiveEvent<Status<BaseResponse<Any>, Any>>()

    fun getUser() = profileRepository.getUser()

    fun doLogout() {
        subscribe(profileRepository.logout(), logoutStatus)
    }
}
