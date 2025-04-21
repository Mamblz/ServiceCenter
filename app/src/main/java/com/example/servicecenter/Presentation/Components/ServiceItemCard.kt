package com.example.servicecenter.Presentation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    serviceItem: ServiceItem,
    getImageUrl: (String) -> String = { serviceItem.imageUrl ?: "" }
) {
    var imageUrl by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
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

            LaunchedEffect(serviceItem) {
                imageUrl = getImageUrl(serviceItem.id)
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
                    text = "Категория: ${serviceItem.category.name}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
