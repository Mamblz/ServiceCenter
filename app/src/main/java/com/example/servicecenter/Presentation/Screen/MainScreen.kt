package com.example.servicecenter.Presentation.Screens.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app.presentation.screens.CustomTextField
import com.example.servicecenter.Domain.Utils.ResultState
import com.example.servicecenter.Presentation.Components.ServiceCategoryItem
import com.example.servicecenter.Presentation.Components.ServiceItemCard
import com.example.servicecenter.Presentation.ViewModels.ViewModelMain


@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: ViewModelMain = viewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val resultState by viewModel.resultState.collectAsState()
    val filteredItems by viewModel.filteredItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val categories = listOf("Все") + filteredItems.map { it.categoryId.toString() }.distinct()

    // Новые цвета для стиля
    val primaryColor = Color(0xFF4A6FA5)  // Приятный синий
    val secondaryColor = Color(0xFF166088)
    val backgroundColor = Color(0xFFF8F9FA)  // Очень светлый серый
    val cardColor = Color.White
    val textColor = Color(0xFF333333)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)  // Добавили тень
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomTextField(
                    value = searchQuery,
                    onValueChange = { newText -> viewModel.updateSearchQuery(newText) },
                    label = "Поиск услуг",
                    modifier = Modifier.fillMaxWidth()
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(categories) { category ->
                        ServiceCategoryItem(
                            category = category,
                            isSelected = selectedCategory == category || (selectedCategory == null && category == "Все"),
                            onClick = {
                                if (category == "Все") {
                                    viewModel.updateCategory(null)
                                } else {
                                    viewModel.updateCategory(category)
                                }
                            },
                            primaryColor = primaryColor
                        )
                    }
                }

                when (val state = resultState) {
                    is ResultState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredItems) { serviceItem ->
                                ServiceItemCard(
                                    serviceItem = serviceItem,
                                )
                            }
                        }
                    }
                    is ResultState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = primaryColor,
                                strokeWidth = 4.dp
                            )
                        }
                    }
                    is ResultState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Ошибка загрузки данных",
                                color = Color(0xFFD32F2F),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    is ResultState.Initialized -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Загрузка данных...",
                                color = textColor,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadServiceItems()
    }
}

