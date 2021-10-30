package com.siemens.leadx.ui.details

import com.siemens.leadx.data.local.entities.LeadStatusType
import com.siemens.leadx.data.remote.apicalls.LeadApiCalls
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class LeadDetailsRepository @Inject constructor(private val leadApiCalls: LeadApiCalls) :
    BaseRepository() {

    fun getLead(id: String) = leadApiCalls.getLead(id)
        .doOnSuccess {
            it.data?.let { lead ->
                val currentStatusId = lead.leadStatusId
                if (currentStatusId != null && currentStatusId != LeadStatusType.REJECTED)
                    lead.leadStatusListWithSelectedCurrentStatus =
                        LeadStatusType.getLeadStatusListWithOutReject(currentStatusId)
            }
        }
}
