package com.siemens.leadx.data.remote.entites

import com.google.gson.annotations.SerializedName

data class Notification(

	@field:SerializedName("lead_status_id")
	val leadStatusId: Int? = null,

	@field:SerializedName("lead_status")
	val leadStatus: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("sent_on")
	val sentOn: Long? = null,

	@field:SerializedName("lead_id")
	val leadId: Int? = null,
)
