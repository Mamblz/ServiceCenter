package com.example.servicecenter.apiconnect.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ServiceItem(
    val id: Long,
    val name: String,
    val description: String?,
    val price: Double,
    @SerialName("category_id")
    val categoryId: Int,
    @SerialName("image_url")
    val imageUrl: String?
)


