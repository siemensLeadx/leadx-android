package com.siemens.leadx.data.remote.entites

import com.google.gson.annotations.SerializedName

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
)
