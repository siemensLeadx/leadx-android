package com.siemens.leadx.utils.extensions

import android.os.Bundle
import com.siemens.leadx.data.local.entities.CreateLookUps
import com.siemens.leadx.data.local.entities.OnBoardingEntity

private const val EXTRA_ID = "EXTRA_ID"
var Bundle.id: String
    get() = getString(EXTRA_ID, "")
    set(value) {
        putString(EXTRA_ID, value)
    }

private const val EXTRA_CREATE_LOOKUP = "EXTRA_CREATE_LOOKUP"
var Bundle.createLookups: CreateLookUps
    get() = getString(EXTRA_CREATE_LOOKUP).toObjectFromJson(CreateLookUps::class.java)
    set(value) {
        putString(EXTRA_CREATE_LOOKUP, value.toJsonString())
    }

private const val EXTRA_WEB_VIEW_TITLE = "EXTRA_WEB_VIEW_TITLE"
var Bundle.webViewTitle: String
    get() = getString(EXTRA_WEB_VIEW_TITLE, "")
    set(value) {
        putString(EXTRA_WEB_VIEW_TITLE, value)
    }

private const val EXTRA_WEB_VIEW_URL = "EXTRA_WEB_VIEW_URL"
var Bundle.webViewUrl: String
    get() = getString(EXTRA_WEB_VIEW_URL, "")
    set(value) {
        putString(EXTRA_WEB_VIEW_URL, value)
    }

private const val EXTRA_ON_BOARDING = "EXTRA_ON_BOARDING"
var Bundle.onBoardingEntity: OnBoardingEntity
    get() = getString(EXTRA_ON_BOARDING).toObjectFromJson(OnBoardingEntity::class.java)
    set(value) {
        putString(EXTRA_ON_BOARDING, value.toJsonString())
    }
