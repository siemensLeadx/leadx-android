package com.siemens.leadx.ui.createlead

import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.FieldError
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateLeadViewModel @Inject constructor(private val repository: CreateLeadRepository) :
    BaseViewModel(repository) {

    val createLeadStatus = SingleLiveEvent<Status<BaseResponse<Any>, Any>>()
    val fieldError = SingleLiveEvent<FieldError>()

    fun createLead(
        name: String,
        hospitalName: String,
        region: String,
        contactPerson: String,
        comment: String,
        devices: List<Int>?,
    ) {
        fieldError.value = FieldError.None
        validateName(name)
        validateHospitalName(hospitalName)
        validateRegion(region)
        validateCustomerStatus()
        validateDate()
        validateContactPerson(contactPerson)

        if (fieldError.value == FieldError.None && !devices.isNullOrEmpty())
            subscribe(
                repository.createLead(
                    name.trim(),
                    hospitalName.trim(),
                    region.trim(),
                    contactPerson.trim(), devices, comment.trim()
                ),
                createLeadStatus
            )
    }

    private fun validateName(value: String) {
        if (value.isBlank())
            fieldError.value =
                FieldError.NameError(repository.getString(R.string.msg_empty_field))
    }

    private fun validateHospitalName(value: String) {
        if (value.isBlank())
            fieldError.value =
                FieldError.HospitalNameError(repository.getString(R.string.msg_empty_field))
    }

    private fun validateRegion(value: String) {
        if (value.isBlank())
            fieldError.value =
                FieldError.RegionError(repository.getString(R.string.msg_empty_field))
    }

    private fun validateContactPerson(value: String) {
        if (value.isBlank())
            fieldError.value =
                FieldError.ContactPersonError(repository.getString(R.string.msg_empty_field))
    }

    private fun validateDate() {
        if (repository.createLeadRequest.customer_due_date == null)
            fieldError.value =
                FieldError.DateError(repository.getString(R.string.msg_empty_field))
    }

    private fun validateCustomerStatus() {
        if (repository.createLeadRequest.customer_status == null)
            fieldError.value =
                FieldError.CustomerStatusError(repository.getString(R.string.msg_empty_field))
    }

    fun setBusinessOpportunity(id: Int) {
        repository.createLeadRequest.business_opportunity_type = id
    }

    fun setCustomerStatus(id: Int) {
        repository.createLeadRequest.customer_status = id
    }

    fun getCustomerDueDate() = repository.createLeadRequest.customer_due_date?.times(1000) ?: 0L

    fun setCustomerDueDate(date: Long) {
        repository.createLeadRequest.customer_due_date = date / 1000
    }
}
