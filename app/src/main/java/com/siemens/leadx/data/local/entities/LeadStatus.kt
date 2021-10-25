package com.siemens.leadx.data.local.entities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LeadStatus(
    val id: Int,
    @StringRes var title: Int,
) {
    @DrawableRes
    var background: Int? = null

    @ColorRes
    var numberColor: Int? = null

    @ColorRes
    var textColor: Int? = null
}
