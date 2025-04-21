package com.example.app.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.example.app.presentation.navigation.NavigationRoutes

@Composable
fun SignInScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Авторизация",
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Пароль") },
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        errorMessage = Check(email, password)
                        if (errorMessage.isNullOrEmpty()) {
                            navController.navigate(NavigationRoutes.MainScreen)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
                ) {
                    Text("Войти", color = Color.White)
                }

                if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                ClickableText(
                    text = AnnotatedString("Забыли пароль?"),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF007AFF),
                        textDecoration = TextDecoration.Underline
                    ),
                    onClick = {}
                )

                ClickableText(
                    text = AnnotatedString("Регистрация"),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF007AFF),
                        textDecoration = TextDecoration.Underline
                    ),
                    onClick = {
                        navController.navigate(NavigationRoutes.SignUpScreen)
                    }
                )
            }
        }
    }
}

fun Check(email: String, password: String): String? {
    if (email.isEmpty()) return "Email не может быть пустым"
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return "Неверный формат email"
    if (password.isEmpty()) return "Пароль не может быть пустым"
    if (password.length < 6) return "Пароль должен содержать минимум 6 символов"
    return null
}
