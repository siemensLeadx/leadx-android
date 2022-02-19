package com.siemens.leadx.data.local.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingEntity(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
)
