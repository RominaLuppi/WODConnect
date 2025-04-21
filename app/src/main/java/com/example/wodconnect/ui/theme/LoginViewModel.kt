package com.example.wodconnect.ui.theme


import android.util.Patterns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _correctLogin = MutableLiveData<Boolean>()
    val correctLogin: LiveData<Boolean> = _correctLogin

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun onLoginSelected(onLoginSucess: () -> Unit) {
        val currentEmail = _email.value ?: ""
        val currentPassword = _password.value ?: ""

        if(isValidEmail(currentEmail) && isValidPassword(currentPassword)) {
            _correctLogin.value = true

            viewModelScope.launch {
                delay(2000)
               // _correctLogin.value = false
                onLoginSucess()
            }
        }else{
            _email.value = ""
            _password.value = ""
            _correctLogin.value = false
        }
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()


    private fun isValidPassword(password: String) = password.length >= 8


}