package com.siemens.leadx.ui.profile

import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    BaseViewModel(profileRepository) {

    fun getUser() = profileRepository.getUser()
}
