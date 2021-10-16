package com.siemens.leadx.data.remote

import com.google.gson.annotations.SerializedName

open class ErrorResponse<D> {

    @field:SerializedName("code")
    var code: Int = 0

    @field:SerializedName("message")
    var message: String = ""

    @field:SerializedName("data")
    var data: D? = null

    @field:SerializedName("errors")
    val errors: List<Map<String, String>>? = null

    var showOnlyErrorMsg: Boolean = false
}
