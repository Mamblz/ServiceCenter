package com.example.servicecenter.apiconnect.model

import com.example.servicecenter.apiconnect.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ServiceItem(
    val id: String,

    @SerialName("service_name")
    val name: String,

    val description: String?,

    val price: Double,

    @SerialName("category")
    val category: Category,

    @SerialName("image_url")
    val imageUrl: String?
)
