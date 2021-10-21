package com.siemens.leadx.data.remote.api

import com.siemens.leadx.data.remote.ApiUrls
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.LookUp
import io.reactivex.Single
import retrofit2.http.GET

interface LeadApi {

    @GET(ApiUrls.BUSINESS_OPPORTUNITIES)
    fun executeGetBusinessOpportunity(): Single<BaseResponse<List<LookUp>>>

    @GET(ApiUrls.CUSTOMER_STATUS)
    fun executeGetCustomerStatus(): Single<BaseResponse<List<LookUp>>>
}