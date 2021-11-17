package com.siemens.leadx.data.remote.requests

data class CreateLeadRequest(
    var contact_person: String? = null,
    var devices: List<Int>? = null,
    var customer_status: Int? = null,
    var lead_name: String? = null,
    var comment: String? = null,
    var region: Int? = null,
    var business_opportunity_type: Int? = null,
    var customer_due_date: Long? = null,
    var hospital_name: String? = null,
    var sector: Int? = null,
    var city: String? = null,
)
