package com.example.servicecenter.Presentation.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSearch(
    value: String,
    onvaluechange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            onvaluechange(newText)
        },
        modifier = modifier
            .padding(horizontal = 16.dp),
        label = { Text("Поиск") },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            containerColor = Color.White
        ),
    )
}
