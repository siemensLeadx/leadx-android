package com.siemens.leadx.ui.main

import com.siemens.leadx.data.local.entities.CreateLookUps
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.LookUp
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel(mainRepository) {

    val createLookUpsStatus = SingleLiveEvent<Status<BaseResponse<CreateLookUps>, Any>>()
    private var createLookUps: BaseResponse<CreateLookUps>? = null

    fun getUser() = mainRepository.getUser()

    fun getCreateLookups() {
        subscribe(if (createLookUps != null)
            Single.just(requireNotNull(createLookUps))
        else returnZippedResponse(),
            createLookUpsStatus)
    }

    private fun returnZippedResponse(): Single<BaseResponse<CreateLookUps>> {
        return Single.zip(mainRepository.getBusinessOpportunities()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()),
            mainRepository.getCustomerStatus()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()),
            mainRepository.getDevices()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()),
            Function3 {
                    t1: BaseResponse<List<LookUp>>,
                    t2: BaseResponse<List<LookUp>>,
                    t3: BaseResponse<List<LookUp>>,
                ->
                return@Function3 BaseResponse<CreateLookUps>().also {
                    it.data =
                        CreateLookUps(
                            t1.data,
                            t2.data,
                            t3.data
                        )
                    createLookUps = it
                }
            }
        )
    }
}
