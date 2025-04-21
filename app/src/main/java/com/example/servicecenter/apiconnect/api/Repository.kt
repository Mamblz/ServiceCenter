package com.example.servicecenter.api

import ServiceItem
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {

    suspend fun getServiceItems(search: String?, category: String?): Flow<Result<List<ServiceItem>>>
}
