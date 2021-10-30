package com.siemens.leadx.ui.notifications.paging

import androidx.lifecycle.MutableLiveData
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Notification
import com.siemens.leadx.ui.notifications.NotificationsRepository
import com.siemens.leadx.utils.Constants.FIRST_PAGE
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseDataSource

class NotificationsDataSource(
    private val repository: NotificationsRepository,
    status: MutableLiveData<Status<BaseResponse<List<Notification>>, Any>>,
) :
    BaseDataSource<Notification, BaseResponse<List<Notification>>, Any>(repository, status) {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Notification>,
    ) {
        subscribe(
            repository.getNotifications(FIRST_PAGE),

            { loadInitial(params, callback) },
            {
                callback.onResult(it?.data ?: ArrayList(), null, FIRST_PAGE + 1)
            },
            false
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Notification>) {
        subscribe(
            repository.getNotifications(params.key.toInt()),
            { loadAfter(params, callback) },
            {
                callback.onResult(it?.data ?: ArrayList(), params.key.toInt() + 1)
            },
            true
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Notification>) {
        // pass
    }
}
