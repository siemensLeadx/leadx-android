package com.siemens.leadx.data.remote.api

import com.siemens.leadx.data.remote.ApiUrls
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.LookUp
import com.siemens.leadx.data.remote.requests.CreateLeadRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LeadApi {

    @GET(ApiUrls.BUSINESS_OPPORTUNITIES)
    fun executeGetBusinessOpportunity(): Single<BaseResponse<List<LookUp>>>

    @GET(ApiUrls.CUSTOMER_STATUS)
    fun executeGetCustomerStatus(): Single<BaseResponse<List<LookUp>>>

    @GET(ApiUrls.DEVICES)
    fun executeGetDevices(): Single<BaseResponse<List<LookUp>>>

    @POST(ApiUrls.CREATE_LEAD)
    fun executeCreateLead(@Body createLeadRequest: CreateLeadRequest): Single<BaseResponse<Any>>
}