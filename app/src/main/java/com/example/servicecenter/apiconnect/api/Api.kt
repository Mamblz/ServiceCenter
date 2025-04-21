package com.example.servicecenter.apiconnect.api

import ServiceItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("rest/v1/service_items")
    suspend fun getServiceItems(
        @Query("search") search: String? = null,
        @Query("category") category: String? = null
    ): List<ServiceItem>

    companion object {
        const val BASE_URL = "https://iqxhbyhwbcxygishldsx.supabase.co"
    }
}


