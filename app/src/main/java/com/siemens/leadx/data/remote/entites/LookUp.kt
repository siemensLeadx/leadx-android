package com.siemens.leadx.data.remote.entites

import com.google.gson.annotations.SerializedName

data class LookUp(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,
)
