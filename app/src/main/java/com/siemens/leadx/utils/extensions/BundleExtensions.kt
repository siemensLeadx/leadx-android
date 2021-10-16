package com.siemens.leadx.utils.extensions

import android.os.Bundle

private const val EXTRA_ID = "EXTRA_ID"
var Bundle.id: Int
    get() = getInt(EXTRA_ID, -1)
    set(value) {
        putInt(EXTRA_ID, value)
    }
