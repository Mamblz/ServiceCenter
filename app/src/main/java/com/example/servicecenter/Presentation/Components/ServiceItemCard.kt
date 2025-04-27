package com.example.servicecenter.Presentation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.servicecenter.apiconnect.model.ServiceItem

@Composable
fun ServiceItemCard(
    serviceItem: ServiceItem,
    onDelete: (ServiceItem) -> Unit,
    onEdit: (ServiceItem) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(model = serviceItem.imageUrl)

            Image(
                painter = painter,
                contentDescription = serviceItem.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = serviceItem.name, style = MaterialTheme.typography.titleMedium)
                Text(text = serviceItem.description ?: "", style = MaterialTheme.typography.bodySmall)
                Text(text = "Цена: ${serviceItem.price} ₽", style = MaterialTheme.typography.bodySmall)
                Text(text = "Категория: ${serviceItem.categoryId}", style = MaterialTheme.typography.bodySmall)
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { onEdit(serviceItem) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Редактировать")
                }
                IconButton(onClick = { onDelete(serviceItem) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
        }
    }
}

