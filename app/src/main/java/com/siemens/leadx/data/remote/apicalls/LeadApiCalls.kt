package com.siemens.leadx.data.remote.apicalls

import com.siemens.leadx.data.remote.api.LeadApi
import com.siemens.leadx.data.remote.requests.CreateLeadRequest
import retrofit2.Retrofit
import javax.inject.Inject

class LeadApiCalls @Inject constructor(retrofit: Retrofit) {

    private val leadApi = retrofit.create(LeadApi::class.java)

    fun getBusinessOpportunities() = leadApi.executeGetBusinessOpportunity()

    fun getCustomerStatus() = leadApi.executeGetCustomerStatus()

    fun getDevices() = leadApi.executeGetDevices()

    fun createLead(createLeadRequest: CreateLeadRequest) =
        leadApi.executeCreateLead(createLeadRequest)

    fun getLeads(
        page: Int,
    ) = leadApi.executeGetLeads(page)

    fun getLead(id: String) = leadApi.executeGetLead(id)
}
