package com.siemens.leadx.data.remote.entites

import com.google.gson.annotations.SerializedName
import com.siemens.leadx.data.local.entities.LeadStatus

data class Lead(

    @field:SerializedName("lead_status")
    val leadStatus: String? = null,

    @field:SerializedName("lead_status_text_color")
    val leadStatusTextColor: String? = null,

    @field:SerializedName("created_on")
    val createdOn: Long? = null,

    @field:SerializedName("lead_status_back_color")
    val leadStatusBackColor: String? = null,

    @field:SerializedName("lead_name")
    val leadName: String? = null,

    @field:SerializedName("lead_id")
    val leadId: String? = null,

    @field:SerializedName("hospital_name")
    val hospitalName: String? = null,

    @field:SerializedName("region")
    val region: String? = null,

    @field:SerializedName("lead_status_id")
    val leadStatusId: Int? = null,

    @field:SerializedName("lead_status_note")
    val leadStatusNote: String? = null,

    @field:SerializedName("business_opportunity_type")
    val businessOpportunityType: String? = null,

    @field:SerializedName("customer_status")
    val customerStatus: String? = null,

    @field:SerializedName("customer_due_date")
    val customerDueDate: Long? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("contact_person")
    val contactPerson: String? = null,

    @field:SerializedName("devices")
    val devices: List<String>? = null,

    @field:SerializedName("reward")
    val reward: Float? = null,
) {
    var leadStatusListWithSelectedCurrentStatus: List<LeadStatus>? = null
}
