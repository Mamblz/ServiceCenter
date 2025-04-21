package com.example.servicecenter.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.servicecenter.Presentation.ViewModels.SignUpViewModel
import com.example.servicecenter.Presentation.ViewModels.ResultState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.example.app.presentation.navigation.NavigationRoutes

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = viewModel()) {
    val state by viewModel.uiState
    val result by viewModel.resultState.collectAsState()

    LaunchedEffect(result) {
        if (result is ResultState.Success) {
            delay(2000)
            navController.navigate(NavigationRoutes.SignInScreen) {
                popUpTo(NavigationRoutes.SignUpScreen) { inclusive = true }
            }
        }
    }

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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Регистрация",
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = state.name,
                    onValueChange = { viewModel.updateState(state.copy(name = it)) },
                    label = { Text("Ваше имя") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.updateState(state.copy(email = it)) },
                    label = { Text("Почта") },
                    isError = !state.isEmailValid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                if (!state.isEmailValid && state.email.isNotEmpty()) {
                    Text("Некорректный email", color = Color.Red)
                }

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.updateState(state.copy(password = it)) },
                    label = { Text("Пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.updateState(state.copy(confirmPassword = it)) },
                    label = { Text("Подтвердите пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.password.isNotEmpty() && state.confirmPassword.isNotEmpty() && state.password != state.confirmPassword) {
                    Text("Пароли не совпадают", color = Color.Red)
                }

                OutlinedTextField(
                    value = state.city,
                    onValueChange = { viewModel.updateState(state.copy(city = it)) },
                    label = { Text("Город") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = { viewModel.updateState(state.copy(phoneNumber = it)) },
                    label = { Text("Номер телефона") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (result is ResultState.Loading) {
                    CircularProgressIndicator()
                }

                Button(
                    onClick = { viewModel.signUp() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
                ) {
                    Text("Зарегистрироваться")
                }

                ClickableText(
                    text = AnnotatedString("Уже есть аккаунт? Войти"),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF007AFF),
                        textDecoration = TextDecoration.Underline
                    ),
                    onClick = {
                        navController.navigate(NavigationRoutes.SignInScreen)
                    }
                )
            }
        }
    }
}