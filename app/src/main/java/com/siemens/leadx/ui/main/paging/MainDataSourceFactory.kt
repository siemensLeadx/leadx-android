package com.siemens.leadx.ui.main.paging

import androidx.lifecycle.MutableLiveData
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.ui.main.MainRepository
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseDataSourceFactory

class MainDataSourceFactory(
    private val repository: MainRepository,
    private val status: MutableLiveData<Status<BaseResponse<List<Lead>>, Any>>,
) : BaseDataSourceFactory<Lead, BaseResponse<List<Lead>>, Any, MainDataSource>() {

    override fun getDataSource(): MainDataSource {
        return MainDataSource(repository, status)
    }
}
