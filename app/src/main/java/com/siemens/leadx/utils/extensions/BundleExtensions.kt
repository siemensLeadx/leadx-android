package com.siemens.leadx.utils.extensions

import android.os.Bundle
import com.siemens.leadx.data.local.entities.CreateLookUps

private const val EXTRA_ID = "EXTRA_ID"
var Bundle.id: Int
    get() = getInt(EXTRA_ID, -1)
    set(value) {
        putInt(EXTRA_ID, value)
    }


private const val EXTRA_CREATE_LOOKUP = "EXTRA_CREATE_LOOKUP"
var Bundle.createLookups: CreateLookUps
    get() = getString(EXTRA_CREATE_LOOKUP).toObjectFromJson(CreateLookUps::class.java)
    set(value) {
        putString(EXTRA_CREATE_LOOKUP, value.toJsonString())
    }
