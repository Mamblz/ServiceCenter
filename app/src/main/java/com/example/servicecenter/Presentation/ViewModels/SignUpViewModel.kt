package com.example.servicecenter.Presentation.ViewModels

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.domain.state.SignUpState
import com.example.servicecenter.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SignUpViewModel : ViewModel() {
    private val _uiState = mutableStateOf(SignUpState())
    val uiState: State<SignUpState> = _uiState

    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    fun updateState(newState: SignUpState) {
        _uiState.value = newState
        _resultState.value = ResultState.Initialized
    }

    fun signUp() {
        println("Начало регистрации...")


        if (_uiState.value.password.length < 6) {
            _resultState.value = ResultState.Error("Пароль должен быть не менее 6 символов")
            return
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) {
            _resultState.value = ResultState.Error("Некорректный email")
            return
        }

        _resultState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                println("Отправка данных в Supabase...")


                val userData = mapOf(
                    "Id" to UUID.randomUUID().toString(),
                    "name" to _uiState.value.name,
                    "Email" to _uiState.value.email,
                    "Password" to _uiState.value.password,
                    "City" to _uiState.value.city.takeIf { it.isNotBlank() },
                    "PhoneNumber" to _uiState.value.phoneNumber.takeIf { it.isNotBlank() },
                )


                supabase.from("Users").insert(userData)

                _resultState.value = ResultState.Success("Регистрация успешна!")
            } catch (ex: Exception) {
                println("Ошибка: ${ex.stackTraceToString()}")
                _resultState.value = ResultState.Error("Ошибка: ${ex.message}")
            }
        }
    }
}

sealed class ResultState {
    object Initialized : ResultState()
    object Loading : ResultState()
    data class Success(val message: String) : ResultState()
    data class Error(val message: String) : ResultState()
}

