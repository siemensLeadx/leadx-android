package com.siemens.leadx.ui.createlead

import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.apicalls.LeadApiCalls
import com.siemens.leadx.data.remote.entites.LookUp
import com.siemens.leadx.data.remote.requests.CreateLeadRequest
import com.siemens.leadx.utils.base.BaseRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateLeadRepository @Inject constructor(private val leadApiCalls: LeadApiCalls) :
    BaseRepository() {

    private var businessOpportunities: BaseResponse<List<LookUp>>? = null
    private var customerStatus: BaseResponse<List<LookUp>>? = null
    val createLeadRequest = CreateLeadRequest()

    fun getBusinessOpportunities() =
        if (businessOpportunities != null)
            Single.just(businessOpportunities)
        else
            leadApiCalls.getBusinessOpportunities()
                .doOnSuccess {
                    businessOpportunities = it
                }


    fun getCustomerStatus() =
        if (customerStatus != null)
            Single.just(customerStatus)
        else
            leadApiCalls.getCustomerStatus()
                .doOnSuccess {
                    customerStatus = it
                }

}
