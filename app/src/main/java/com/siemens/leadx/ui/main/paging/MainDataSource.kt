package com.siemens.leadx.ui.main.paging

import androidx.lifecycle.MutableLiveData
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.ui.main.MainRepository
import com.siemens.leadx.utils.Constants.FIRST_PAGE
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseDataSource

class MainDataSource(
    private val mainRepository: MainRepository,
    status: MutableLiveData<Status<BaseResponse<List<Lead>>, Any>>,
) :
    BaseDataSource<Lead, BaseResponse<List<Lead>>, Any>(mainRepository, status) {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Lead>,
    ) {
        subscribe(
            mainRepository.getLeads(FIRST_PAGE),
            { loadInitial(params, callback) },
            {
                callback.onResult(it?.data ?: ArrayList(), null, FIRST_PAGE + 1)
            },
            false
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Lead>) {
        subscribe(
            mainRepository.getLeads(params.key.toInt()),
            { loadAfter(params, callback) },
            {
                callback.onResult(it?.data ?: ArrayList(), params.key.toInt() + 1)
            },
            true
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Lead>) {
        // pass
    }
}
