package com.siemens.leadx.ui.notifications

import com.siemens.leadx.data.remote.apicalls.AuthenticationApiCalls
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class NotificationsRepository @Inject constructor(
    private val authenticationApiCalls: AuthenticationApiCalls,
) :
    BaseRepository() {

    fun getNotifications(
        page: Int,
    ) = authenticationApiCalls.getNotifications(page)
}
