package com.example.wodconnect.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wodconnect.data.User
import com.example.wodconnect.modelo.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository

) : ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun login(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            _isLoading.value = false
            result
                .onSuccess { firebaseUser ->
                    _user.value = User(
                        id =  firebaseUser.id,
                        email = firebaseUser.email ?: "",
                        name = firebaseUser.name ?: ""
                    ) }
                .onFailure { exception ->
                    val message = when(exception){
                        is FirebaseAuthInvalidUserException -> "Usuario no registrado. Por favor, regístrate"
                        is FirebaseAuthInvalidCredentialsException -> "Correo y/o contraseña incorrecto"
                        else -> exception.message ?: "Ha ocurrido un error"
                    }
                    _errorMessage.value = exception.message}
        }
    }
    fun cleanError(){
        _errorMessage.value = null
    }

    fun onEmailChanged(newEmail: String){
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String){
        _password.value = newPassword
    }

    //registro de un nuevo usuario en Firebase
    fun register(email: String, password: String){
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            val result = authRepository.register(email, password)
            _isLoading.value = false
            result
                .onSuccess { firebaseUser ->
                _user.value = firebaseUser
            }
                .onFailure { exception ->
                    _errorMessage.value = exception.message
                }
        }
    }


}