package com.siemens.leadx.ui.createlead

import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateLeadViewModel @Inject constructor(private val repository: CreateLeadRepository) :
    BaseViewModel(repository) {

    fun setBusinessOpportunity(id: Int) {
        repository.createLeadRequest.business_opportunity_type = id
    }

    fun setCustomerStatus(id: Int) {
        repository.createLeadRequest.customer_status = id
    }

    fun getCustomerDueDate() = repository.createLeadRequest.customer_due_date

    fun setCustomerDueDate(date: Long) {
        repository.createLeadRequest.customer_due_date = date
    }
}
