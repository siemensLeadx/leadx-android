package com.siemens.leadx.ui.main

import com.siemens.leadx.data.remote.apicalls.LeadApiCalls
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class MainRepository @Inject constructor(private val leadApiCalls: LeadApiCalls) :
    BaseRepository() {

    fun getBusinessOpportunities() =
        leadApiCalls.getBusinessOpportunities()

    fun getCustomerStatus() =
        leadApiCalls.getCustomerStatus()

    fun getDevices() =
        leadApiCalls.getDevices()
}
