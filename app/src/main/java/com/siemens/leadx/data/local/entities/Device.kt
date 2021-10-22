package com.siemens.leadx.data.local.entities

import com.siemens.leadx.data.remote.entites.LookUp

data class Device(var lookUp: LookUp? = null, var showError: Boolean = false)