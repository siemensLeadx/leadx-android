package com.siemens.leadx.ui.createlead

import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.LookUp
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateLeadViewModel @Inject constructor(private val createLeadRepository: CreateLeadRepository) :
    BaseViewModel(createLeadRepository) {

    val businessOpportunitiesStatus =
        SingleLiveEvent<Status<BaseResponse<List<LookUp>>, Any>>()
    val customerStatus = SingleLiveEvent<Status<BaseResponse<List<LookUp>>, Any>>()

    fun getBusinessOpportunities() {
        subscribe(createLeadRepository.getBusinessOpportunities(), businessOpportunitiesStatus)
    }

    fun getCustomerStatus() {
        subscribe(createLeadRepository.getCustomerStatus(), customerStatus)
    }

    fun setBusinessOpportunity(id: Int) {
        createLeadRepository.createLeadRequest.business_opportunity_type = id
    }

    fun setCustomerStatus(id: Int) {
        createLeadRepository.createLeadRequest.customer_status = id
    }

    fun getCustomerDueDate() = createLeadRepository.createLeadRequest.customer_due_date

    fun setCustomerDueDate(date: Long) {
        createLeadRepository.createLeadRequest.customer_due_date = date
    }
}
