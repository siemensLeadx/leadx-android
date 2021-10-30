package com.siemens.leadx.ui.notifications.paging

import androidx.lifecycle.MutableLiveData
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Notification
import com.siemens.leadx.ui.notifications.NotificationsRepository
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseDataSourceFactory

class NotificationsDataSourceFactory(
    private val repository: NotificationsRepository,
    private val status: MutableLiveData<Status<BaseResponse<List<Notification>>, Any>>,
) : BaseDataSourceFactory<Notification, BaseResponse<List<Notification>>, Any, NotificationsDataSource>() {

    override fun getDataSource(): NotificationsDataSource {
        return NotificationsDataSource(repository, status)
    }
}
