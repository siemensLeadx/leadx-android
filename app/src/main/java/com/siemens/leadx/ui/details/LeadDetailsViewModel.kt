package com.siemens.leadx.ui.details

import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeadDetailsViewModel @Inject constructor(private val repository: LeadDetailsRepository) :
    BaseViewModel(repository) {

    val status = SingleLiveEvent<Status<BaseResponse<Lead>, Any>>()

    fun getLead(id: String) {
        subscribe(repository.getLead(id), status)
    }
}
