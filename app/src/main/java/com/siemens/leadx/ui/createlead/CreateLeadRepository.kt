package com.siemens.leadx.ui.createlead

import com.siemens.leadx.data.remote.apicalls.LeadApiCalls
import com.siemens.leadx.data.remote.requests.CreateLeadRequest
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class CreateLeadRepository @Inject constructor(private val leadApiCalls: LeadApiCalls) :
    BaseRepository() {

    val createLeadRequest = CreateLeadRequest()

}
