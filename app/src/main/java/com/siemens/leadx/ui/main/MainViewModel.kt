package com.siemens.leadx.ui.main

import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel(mainRepository) {

    fun getUser() = mainRepository.getUser()
}
