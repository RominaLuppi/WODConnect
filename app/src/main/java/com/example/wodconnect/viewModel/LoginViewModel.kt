package com.example.wodconnect.viewModel


import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wodconnect.modelo.CrossfitApiService
import com.example.wodconnect.modelo.MockLoginApiService
import com.example.wodconnect.modelo.MockUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mockLoginApi: MockLoginApiService

): ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _correctLogin = MutableLiveData(true)
    val correctLogin: LiveData<Boolean> = _correctLogin

    private val _isBtnLoginEnabled = MutableLiveData(false)
    val isBtnLoginEnabled: LiveData<Boolean> = _isBtnLoginEnabled

    private val _loginSucces = MutableLiveData<Boolean>()
    val loginSucces: LiveData<Boolean> = _loginSucces

    val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    private var mockUsers: List<MockUser> = emptyList()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchMockUsers() //se obtienen los usuarios desde la Api Mocky
    }

    // Función para obtener la lista de usuarios desde Mocky
    private fun fetchMockUsers() {
        Log.d("LoginViewModel", "llamada al launch")

        viewModelScope.launch {
            try {
                val response = mockLoginApi.getMockUser()
                Log.d("LoginViewModel", "Usuarios cargados: $mockUsers")

                if (response.isSuccessful) {
                    mockUsers = response.body()?.users ?: emptyList()

                    Log.d("LoginViewModel", "Usuarios obtenidos: ${mockUsers}")
                    _isLoading.value = false

                }else{
                    _loginError.value = "Error al obtener usuarios"
                }

            } catch (e: Exception) {
                _loginError.value = "Error de red: ${e.message}"
                Log.d("LoginViewModel", "Error en fetchMockUsers: ${e.message}")

            }
        }
    }

    // Función que se llama cuando cambian los campos de email o password
    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _correctLogin.value = true
        _isBtnLoginEnabled.value = email.isNotBlank() && password.isNotBlank()
    }

    // Función que se llama cuando el usuario hace clic en el botón de login
    fun onLoginSelected(
        onLoginSucess: () -> Unit,
        onLoginError: (String) -> Unit
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val currentEmail = _email.value ?: ""
                val currentPassword = _password.value ?: ""

                Log.d("LoginViewModel", "Email: $currentEmail, Password: $currentPassword")
                val userMatch =
                    mockUsers.find { it.email == currentEmail && it.password == currentPassword }

                Log.d("LoginViewModel", "Usuario encontrado: $userMatch")

                if (userMatch != null) {
                    _correctLogin.value = true
                    _loginSucces.value = true
                    onLoginSucess()

                } else {
                    _correctLogin.value = false
                    _loginError.value = "Credenciales incorrectas"
                    onLoginError("Credenciales incorrectas")
                }
            }finally {
                _isLoading.value = false
            }
        }

}
    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String) = password.length >= 8

}