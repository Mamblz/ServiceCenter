package com.example.servicecenter.Presentation.Screens.MainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.servicecenter.Presentation.Components.ServiceCategoryItem
import com.example.servicecenter.Presentation.Components.ServiceItemCard
import com.example.servicecenter.Presentation.Components.TextFieldSearch
import com.example.servicecenter.Presentation.ViewModels.ViewModelMain
import com.example.servicecenter.Domain.Utils.ResultState
import kotlinx.coroutines.runBlocking

/**
 * Главный экран приложения сервисного центра.
 * Отображает:
 * - строку поиска,
 * - категории услуг,
 * - список фильтрованных услуг.
 */
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: ViewModelMain = viewModel()
) {
    val searchQuery = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableIntStateOf(-1) }
    val resultState by viewModel.resultState.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val serviceItems by viewModel.filteredItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldSearch(
            value = searchQuery.value,
            onvaluechange = { newText ->
                searchQuery.value = newText
                viewModel.filterList(newText, selectedCategory.intValue)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ⚙️ Обработка состояний
        when (val state = resultState) {
            is ResultState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ResultState.Error -> {
                Text(
                    text = state.message,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is ResultState.Success -> {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(categories) { category ->
                        CategoryItem(
                            category = category.name,
                            isSelected = selectedCategory.intValue == category.id,
                            onClick = {
                                selectedCategory.intValue = category.id
                                viewModel.filterList(
                                    searchQuery.value,
                                    selectedCategory.intValue
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 🛠 Список услуг
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(serviceItems) { service ->
                        ServiceItemCard(serviceItem = service) {
                            runBlocking {
                                navController.navigate("ServiceDetail/${service.id}")
                            }
                        }
                    }
                }
            }

            is ResultState.Initialized -> {
                Text(
                    text = "Подготовка данных...",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadServiceItems()
    }
}
