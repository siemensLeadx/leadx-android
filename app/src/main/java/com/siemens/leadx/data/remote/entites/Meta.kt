package com.siemens.leadx.data.remote.entites

import com.google.gson.annotations.SerializedName

data class Meta(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("totalPages")
    val totalPages: Int? = null,

    @field:SerializedName("hasPrevious")
    val hasPrevious: Boolean? = null,

    @field:SerializedName("hasNext")
    val hasNext: Boolean? = null,
)
