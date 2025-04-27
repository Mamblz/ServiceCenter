package com.example.servicecenter.Presentation.Screens.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.servicecenter.Presentation.Components.ServiceItemDialog
import com.example.servicecenter.Presentation.ViewModels.ViewModelMain
import com.example.servicecenter.apiconnect.model.ServiceItem


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

    val primaryColor = Color(0xFF4A6FA5)
    val backgroundColor = Color(0xFFF8F9FA)
    val cardColor = Color.White
    val textColor = Color(0xFF333333)

    var showDialog by remember { mutableStateOf(false) }
    var selectedItemForEdit by remember { mutableStateOf<ServiceItem?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Список услуг",
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor
                )
                IconButton(
                    onClick = {
                        selectedItemForEdit = null
                        showDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавить услугу",
                        tint = primaryColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                value = searchQuery,
                onValueChange = { newText -> viewModel.updateSearchQuery(newText) },
                label = "Поиск услуг",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(12.dp))

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
                                onDelete = { item -> viewModel.deleteServiceItem(item) },
                                onEdit = { item ->
                                    selectedItemForEdit = item
                                    showDialog = true
                                }
                            )
                        }
                    }
                }
                is ResultState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
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
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ошибка загрузки данных",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                else -> {}
            }
        }

        FloatingActionButton(
            onClick = {
                selectedItemForEdit = null
                showDialog = true
            },
            containerColor = primaryColor,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить услугу")
        }

        if (showDialog) {
            ServiceItemDialog(
                initialItem = selectedItemForEdit,
                onDismiss = { showDialog = false },
                onConfirm = { item ->
                    if (item.id == null || item.id.toInt() == 0) {
                        viewModel.addServiceItem(item)
                    } else {
                        viewModel.updateServiceItem(item)
                    }
                    showDialog = false
                }
            )
        }
    }
}

