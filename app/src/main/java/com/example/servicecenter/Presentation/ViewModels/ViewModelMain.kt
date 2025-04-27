package com.example.servicecenter.Presentation.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicecenter.Domain.Utils.ResultState
import com.example.servicecenter.Domain.Utils.SupabaseClient.supabase
import com.example.servicecenter.apiconnect.model.Category
import com.example.servicecenter.apiconnect.model.ServiceItem
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelMain : ViewModel() {

    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    private val _serviceItems = MutableStateFlow<List<ServiceItem>>(emptyList())
    private var allServiceItems: List<ServiceItem> = emptyList()
    val serviceItems: StateFlow<List<ServiceItem>> get() = _serviceItems.asStateFlow()

    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow<String?>(null)

    val filteredItems: StateFlow<List<ServiceItem>> = combine(
        _serviceItems,
        searchQuery,
        selectedCategory
    ) { serviceItems, query, category ->
        filterList(serviceItems, query, category)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {
        loadServiceItems()
        loadCategories()
    }

    private fun filterList(serviceItems: List<ServiceItem>, query: String, category: String?): List<ServiceItem> {
        return serviceItems.filter { service ->
            val matchesQuery = query.isEmpty() || service.name.contains(query, ignoreCase = true) || service.description?.contains(query, ignoreCase = true) == true
            val matchesCategory = category == null || service.categoryId.toString() == category
            matchesQuery && matchesCategory
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun updateCategory(category: String?) {
        selectedCategory.value = category
    }

    fun loadServiceItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _resultState.value = ResultState.Loading
            try {
                val response = supabase.postgrest
                    .from("serviceitem")
                    .select()
                    .decodeList<ServiceItem>()

                allServiceItems = response
                _serviceItems.value = allServiceItems
                _resultState.value = ResultState.Success("Услуги успешно загружены")
            } catch (e: Exception) {
                _resultState.value = ResultState.Error(e.message ?: "Ошибка загрузки услуг")
                Log.e("LoadServiceItemsError", "Ошибка загрузки услуг: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value = supabase.postgrest.from("category").select().decodeList<Category>()
            } catch (e: Exception) {
                Log.e("LoadCategoriesError", "Ошибка загрузки категорий: ${e.message}")
            }
        }
    }

    suspend fun getServiceImageUrl(serviceName: String): String {
        return withContext(Dispatchers.IO) {
            supabase.storage.from("ServiceImages").publicUrl("$serviceName.png")
        }
    }

    fun deleteServiceItem(serviceItem: ServiceItem) {
        viewModelScope.launch {
            try {
                supabase.postgrest["serviceitem"]
                    .delete {
                        filter {
                            eq("id", serviceItem.id)
                        }
                    }
                loadServiceItems()
            } catch (e: Exception) {
                Log.e("DeleteError", "Ошибка при удалении услуги: ${e.message}")
            }
        }
    }

    fun addServiceItem(serviceItem: ServiceItem) {
        viewModelScope.launch {
            try {
                supabase.postgrest["serviceitem"]
                    .insert(serviceItem)
                loadServiceItems()
            } catch (e: Exception) {
                Log.e("AddError", "Ошибка при добавлении услуги: ${e.message}")
            }
        }
    }

    fun updateServiceItem(serviceItem: ServiceItem) {
        viewModelScope.launch {
            try {
                supabase.postgrest["serviceitem"]
                    .update(serviceItem) {
                        filter {
                            eq("id", serviceItem.id)
                        }
                    }
                loadServiceItems()
            } catch (e: Exception) {
                Log.e("UpdateError", "Ошибка при обновлении услуги: ${e.message}")
            }
        }
    }
}
