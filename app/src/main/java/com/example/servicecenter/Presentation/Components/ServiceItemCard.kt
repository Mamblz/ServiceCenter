package com.example.servicecenter.Presentation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.servicecenter.R
import com.example.servicecenter.apiconnect.model.ServiceItem

@Composable
fun ServiceItemCard(
    serviceItem: ServiceItem
) {
    var imageUrl by remember { mutableStateOf(serviceItem.imageUrl ?: "") }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .size(Size.ORIGINAL)
                    .build()
            ).state

            LaunchedEffect(serviceItem.id) {
                imageUrl = serviceItem.imageUrl ?: ""
            }

            when (imageState) {
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        painter = painterResource(R.drawable.service),
                        contentDescription = serviceItem.name,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = imageState.painter,
                        contentDescription = serviceItem.name,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                else -> {}
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = serviceItem.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = serviceItem.description ?: "Описание отсутствует",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Цена: ${serviceItem.price} ₽",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Категория: ${serviceItem.categoryId}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
