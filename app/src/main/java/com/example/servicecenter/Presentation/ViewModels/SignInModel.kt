import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.domain.state.ResultState
import com.example.app.domain.state.SignInState
import com.example.app.domain.utils.isEmailValid
import com.example.servicecenter.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch


data class User(
    val Id: String,
    val Email: String?,
    val Password: String?,
    val Name: String?,
    val City: String?,
    val PhoneNumber: String?
)

class SignInViewModel : ViewModel() {
    private val _uiState = mutableStateOf(SignInState())
    val uiState: SignInState get() = _uiState.value

    private val _resultState = mutableStateOf<ResultState>(ResultState.Initialized)
    val resultState: ResultState get() = _resultState.value

    private fun onLoading(isLoading: Boolean) {
        _resultState.value = if (isLoading) ResultState.Loading else ResultState.Idle
    }

    private fun onError(message: String) {
        _resultState.value = ResultState.Error(message)
    }

    private suspend fun findUserByEmail(email: String): User? {
        Log.d("SignInViewModel", "Поиск пользователя по email: $email")
        return try {
            val response = supabase.from("Users")
                .select() // Запрашиваем все поля


            Log.d("SignInViewModel", "Ответ от Supabase: $response")

            val users = response.decodeList<User>()
            Log.d("SignInViewModel", "Пользователи из базы: ${users.size}")


            users.find { it.Email == email.trim() }
        } catch (e: Exception) {
            Log.e("SignInViewModel", "Ошибка поиска пользователя", e)
            null
        }
    }


    fun signIn(email: String, password: String) {
        if (email.isEmailValid() && password.isNotEmpty()) {
            onLoading(true)

            viewModelScope.launch {
                try {

                    val user = findUserByEmail(email)

                    if (user == null) {
                        Log.d("SignInViewModel", "Пользователь не найден по email: $email")
                        onError("Пользователь не найден")
                    } else {

                        if (user.Password != password) {
                            Log.d("SignInViewModel", "Неверный пароль для email: $email")
                            onError("Неверный пароль")
                        } else {
                            Log.d("SignInViewModel", "Успешный вход пользователя с email: ${user.Email}")
                            _resultState.value = ResultState.Success("Успешный вход")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("SignInViewModel", "Ошибка: ${e.message}")
                    onError("Ошибка входа: ${e.message ?: "Неизвестная ошибка"}")
                } finally {
                    onLoading(false)
                }
            }
        } else {
            onError("Введите корректные данные")
        }
    }
}
