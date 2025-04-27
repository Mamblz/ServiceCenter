package com.example.servicecenter.Presentation.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicecenter.apiconnect.model.ServiceItem

@Composable
fun CategoryFilter(selectedCategory: String?, onCategorySelected: (String?) -> Unit) {
    val categories = listOf("Все", "Ремонт телефонов", "Диагностика", "Ремонт ноутбуков", "Обслуживание", "ПО и настройки")

    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        categories.forEach { category ->
            Button(
                onClick = { onCategorySelected(if (category == "Все") null else category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(category)
            }
        }
    }
}

@Composable
fun ServiceItemFilter(service: ServiceItem) {
    if (service.name.isEmpty()) {
        return
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = service.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Категория: ${service.categoryId}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Цена: ${service.price} руб.", style = MaterialTheme.typography.bodySmall)
        }
    }
}
