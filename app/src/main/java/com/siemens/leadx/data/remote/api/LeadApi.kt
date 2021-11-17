package com.siemens.leadx.data.remote.api

import com.siemens.leadx.data.remote.ApiUrls
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.RemoteKeys.ID
import com.siemens.leadx.data.remote.RemoteKeys.PAGE
import com.siemens.leadx.data.remote.RemoteKeys.PER_PAGE
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.data.remote.entites.LookUp
import com.siemens.leadx.data.remote.requests.CreateLeadRequest
import com.siemens.leadx.utils.Constants.PAGE_SIZE
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LeadApi {

    @GET(ApiUrls.BUSINESS_OPPORTUNITIES)
    fun executeGetBusinessOpportunity(): Single<BaseResponse<List<LookUp>>>

    @GET(ApiUrls.CUSTOMER_STATUS)
    fun executeGetCustomerStatus(): Single<BaseResponse<List<LookUp>>>

    @GET(ApiUrls.DEVICES)
    fun executeGetDevices(): Single<BaseResponse<List<LookUp>>>

    @GET(ApiUrls.REGIONS)
    fun executeGetRegions(): Single<BaseResponse<List<LookUp>>>

    @GET(ApiUrls.SECTORS)
    fun executeGetSectors(): Single<BaseResponse<List<LookUp>>>

    @POST(ApiUrls.CREATE_LEAD)
    fun executeCreateLead(@Body createLeadRequest: CreateLeadRequest): Single<BaseResponse<Any>>

    @GET(ApiUrls.GET_LEADS)
    fun executeGetLeads(
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int = PAGE_SIZE,
    ): Single<BaseResponse<List<Lead>>>

    @GET(ApiUrls.GET_LEAD_BY_ID)
    fun executeGetLead(@Path(ID) id: String): Single<BaseResponse<Lead>>
}
