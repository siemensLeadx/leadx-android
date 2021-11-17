package com.siemens.leadx.ui.main

import com.siemens.leadx.data.local.entities.FireBaseToken
import com.siemens.leadx.data.remote.apicalls.AuthenticationApiCalls
import com.siemens.leadx.data.remote.apicalls.LeadApiCalls
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val leadApiCalls: LeadApiCalls,
    private val authenticationApiCalls: AuthenticationApiCalls,
) :
    BaseRepository() {

    fun getBusinessOpportunities() =
        leadApiCalls.getBusinessOpportunities()

    fun getCustomerStatus() =
        leadApiCalls.getCustomerStatus()

    fun getDevices() =
        leadApiCalls.getDevices()

    fun getRegions() =
        leadApiCalls.getRegions()

    fun getSectors() =
        leadApiCalls.getSectors()

    fun getLeads(
        page: Int,
    ) = leadApiCalls.getLeads(page)

    fun getFireBaseToken() = localDataUtils.sharedPrefsUtils.getFireBaseToken()

    fun setFireBaseToken(fireBaseToken: FireBaseToken) =
        localDataUtils.sharedPrefsUtils.setFireBaseToken(fireBaseToken)

    fun registerFireBaseToken(token: String) =
        authenticationApiCalls.addFirebaseToken(
            localDataUtils.getDeviceId(),
            token,
        )
}
