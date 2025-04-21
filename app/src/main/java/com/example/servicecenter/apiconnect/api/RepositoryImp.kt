package com.example.servicecenter.api

import ServiceItem
import com.example.servicecenter.Domain.Utils.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ServiceRepositoryImpl : ServiceRepository {
    override suspend fun getServiceItems(search: String?, category: String?): Flow<Result<List<ServiceItem>>> {
        return flow {
            try {
                val response = withContext(Dispatchers.IO) {
                    SupabaseClient.serviceItems
                        .select()
                        .decodeList<ServiceItem>()
                }
                emit(Result.Success(response))
            } catch (e: IOException) {
                emit(Result.Error(message = "Ошибка загрузки данных"))
            } catch (e: HttpException) {
                emit(Result.Error(message = "Ошибка сервера"))
            } catch (e: Exception) {
                emit(Result.Error(message = "Неизвестная ошибка: ${e.localizedMessage}"))
            }
        }
    }
}

