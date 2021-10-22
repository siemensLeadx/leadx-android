package com.siemens.leadx.data.remote

import com.siemens.leadx.data.remote.RemoteKeys.ID

object ApiUrls {

    const val LOGIN = "api/v1/users/login"
    const val BUSINESS_OPPORTUNITIES = "api/v1/lookups/business_opportunities"
    const val CUSTOMER_STATUS = "api/v1/lookups/customer_status"
    const val DEVICES = "api/v1/lookups/devices"
    const val CREATE_LEAD = "api/v1/leads"
    const val GET_LEADS = "api/v1/leads"
    const val GET_LEAD_BY_ID = "api/v1/leads/{$ID}"
}
