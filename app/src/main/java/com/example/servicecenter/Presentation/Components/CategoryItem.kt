package com.example.servicecenter.Presentation.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Элемент выбора категории услуги сервисного центра
 *
 * @param category Название категории услуги
 * @param isSelected true, если категория выбрана, влияет на внешний вид
 * @param onClick Обработчик клика по категории
 */
@Composable
fun ServiceCategoryItem(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    primaryColor: Color
) {
    val backgroundColor = if (isSelected) primaryColor.copy(alpha = 0.2f) else Color.Transparent
    val textColor = if (isSelected) primaryColor else Color(0xFF666666)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isSelected) primaryColor else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelLarge,
            color = textColor
        )
    }
}
