package com.siemens.leadx.ui.notifications

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Notification
import com.siemens.leadx.ui.notifications.paging.NotificationsDataSourceFactory
import com.siemens.leadx.utils.Constants
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val notificationsRepository: NotificationsRepository) :
    BaseViewModel(notificationsRepository) {

    var pagedList: LiveData<PagedList<Notification>>? = null
    val status = SingleLiveEvent<Status<BaseResponse<List<Notification>>, Any>>()
    private var factory: NotificationsDataSourceFactory? = null

    fun initPagedList() {
        factory = NotificationsDataSourceFactory(notificationsRepository, status)
        pagedList =
            LivePagedListBuilder(requireNotNull(factory), Constants.PAGE_SIZE).build()
    }

    fun refresh(isForce: Boolean) {
        factory?.refresh(isForce)
    }

    fun retry() {
        factory?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        factory?.onCleared()
    }
}
