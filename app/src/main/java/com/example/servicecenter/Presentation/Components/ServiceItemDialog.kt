package com.example.servicecenter.Presentation.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.servicecenter.apiconnect.model.ServiceItem
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceItemDialog(
    initialItem: ServiceItem?,
    onDismiss: () -> Unit,
    onConfirm: (ServiceItem) -> Unit
) {
    var name by remember { mutableStateOf(initialItem?.name ?: "") }
    var price by remember { mutableStateOf(initialItem?.price?.toString() ?: "") }
    var description by remember { mutableStateOf(initialItem?.description ?: "") }
    var categoryId by remember { mutableStateOf(initialItem?.categoryId?.toString() ?: "") }
    var imageUrl by remember { mutableStateOf(initialItem?.imageUrl ?: "") }

    val isEditing = initialItem != null

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    val newItem = ServiceItem(
                        id = initialItem?.id ?: 0,
                        name = name,
                        description = description,
                        price = price.toDoubleOrNull() ?: 0.0,
                        categoryId = categoryId.toIntOrNull() ?: 0,
                        imageUrl = imageUrl
                    )
                    onConfirm(newItem)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007AFF),
                    contentColor = Color.White
                )
            ) {
                Text("Сохранить", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007AFF),
                    contentColor = Color.White
                )
            ) {
                Text("Отмена", color = Color.White)
            }
        },
        title = {
            Text(
                text = if (isEditing) "Редактировать услугу" else "Добавить услугу",
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название услуги") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(Modifier.background(Color.White, RoundedCornerShape(8.dp))),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF0F0F0),
                        focusedLabelColor = Color(0xFF007AFF),
                        focusedIndicatorColor = Color(0xFF007AFF),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Цена") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(Modifier.background(Color.White, RoundedCornerShape(8.dp))),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF0F0F0),
                        focusedLabelColor = Color(0xFF007AFF),
                        focusedIndicatorColor = Color(0xFF007AFF),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Описание") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(Modifier.background(Color.White, RoundedCornerShape(8.dp))),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF0F0F0),
                        focusedLabelColor = Color(0xFF007AFF),
                        focusedIndicatorColor = Color(0xFF007AFF),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
                TextField(
                    value = categoryId,
                    onValueChange = { categoryId = it },
                    label = { Text("ID категории") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(Modifier.background(Color.White, RoundedCornerShape(8.dp))),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF0F0F0),
                        focusedLabelColor = Color(0xFF007AFF),
                        focusedIndicatorColor = Color(0xFF007AFF),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
                TextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Ссылка на изображение") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(Modifier.background(Color.White, RoundedCornerShape(8.dp))),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF0F0F0),
                        focusedLabelColor = Color(0xFF007AFF),
                        focusedIndicatorColor = Color(0xFF007AFF),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
            }
        },
        containerColor = Color(0xFFF0F0F0),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp,
    )
}
