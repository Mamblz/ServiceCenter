package com.example.servicecenter.Presentation.ViewModels

import ServiceItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class ViewModelMain : ViewModel() {

    private val _serviceItems = MutableStateFlow<List<ServiceItem>>(emptyList())
    val serviceItems: StateFlow<List<ServiceItem>> = _serviceItems.asStateFlow()

    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow<String?>(null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _logs = MutableStateFlow("")
    val logs: StateFlow<String> = _logs

    val filteredItems: StateFlow<List<ServiceItem>> = combine(
        _serviceItems, searchQuery, selectedCategory
    ) { items, query, category ->
        items.filter { service ->
            service.name.contains(query, ignoreCase = true) &&
                    (category == null || service.category == category)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun logMessage(message: String) {
        _logs.value = _logs.value + message + "\n"
    }

    fun loadServiceItems() {
        _isLoading.value = true
        logMessage("Загрузка данных из локального хранилища...")

        val result = listOf(
            ServiceItem(
                id = "123",
                name = "Ремонт телефонов",
                description = "Ремонт и диагностика мобильных устройств всех марок.",
                price = 1500.0,
                category = "Телефоны",
                imageUrl = "https://avatars.mds.yandex.net/get-altay/6314780/2a000001820c7ba4f8a3db870174432fe454/XXXL"
            ),
            ServiceItem(
                id = "124",
                name = "Настройка компьютеров",
                description = "Установка операционной системы и настройка ПО.",
                price = 2000.0,
                category = "Компьютеры",
                imageUrl = "https://avatars.mds.yandex.net/get-altay/2424821/2a00000176b3abd1b932f81941f58fb28915/orig"
            ),
            ServiceItem(
                id = "125",
                name = "Чистка ноутбуков",
                description = "Чистка от пыли и замена термопасты для продления срока службы.",
                price = 1000.0,
                category = "Ноутбуки",
                imageUrl = "https://avatars.mds.yandex.net/get-altay/6057477/2a0000018176773f8cb2be06b76dc885d92e/XXXL"
            ),
            ServiceItem(
                id = "126",
                name = "Ремонт принтеров",
                description = "Ремонт лазерных и струйных принтеров, заправка картриджей.",
                price = 800.0,
                category = "Принтеры",
                imageUrl = "https://avatars.mds.yandex.net/get-altay/6200226/2a00000183413b2865c4e13f97d891f4aeb4/XXL_height"
            )
        )

        _serviceItems.value = result
        _isLoading.value = false
        logMessage("Загружено ${result.size} услуг")
    }

    fun updateCategory(category: String?) {
        selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}
