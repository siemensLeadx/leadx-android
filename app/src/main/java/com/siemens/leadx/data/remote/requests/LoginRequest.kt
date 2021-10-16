package com.siemens.leadx.data.remote.requests

data class LoginRequest(
    val login_provider_id: String? = null,
    val last_name: String? = null,
    val first_name: String? = null,
    val email: String? = null,
)