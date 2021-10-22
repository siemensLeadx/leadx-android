package com.siemens.leadx.ui.createlead

import com.siemens.leadx.data.remote.apicalls.LeadApiCalls
import com.siemens.leadx.data.remote.requests.CreateLeadRequest
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class CreateLeadRepository @Inject constructor(private val leadApiCalls: LeadApiCalls) :
    BaseRepository() {

    val createLeadRequest = CreateLeadRequest()

    fun createLead(
        name: String,
        hospitalName: String,
        region: String,
        contactPerson: String,
        devices: List<Int>?,
        comment: String,
    ) = leadApiCalls.createLead(
        createLeadRequest.also {
            it.lead_name = name
            it.hospital_name = hospitalName
            it.region = region
            it.contact_person = contactPerson
            it.devices = devices
            it.comment = comment
        }
    )
}
