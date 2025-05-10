package com.example.wodconnect.viewModel



import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(


): ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _correctLogin = MutableLiveData(true)
    val correctLogin: LiveData<Boolean> = _correctLogin

    private val _isBtnLoginEnabled = MutableLiveData(false)
    val isBtnLoginEnabled: LiveData<Boolean> = _isBtnLoginEnabled

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _correctLogin.value = true
        _isBtnLoginEnabled.value = true
    }

    fun onLoginSelected(onLoginSucess: () -> Unit, onLoginError: () -> Unit) {
        val currentEmail = _email.value ?: ""
        val currentPassword = _password.value ?: ""

//        if(isValidEmail(currentEmail) && isValidPassword(currentPassword)) {
//            _correctLogin.value = true
//
////            viewModelScope.launch {
////                delay(2000)
////                val loginSuccess = authRepository.login(currentEmail, currentPassword)
////                if(loginSuccess){
////                    onLoginSucess()
////                }else{
////                    _correctLogin.value = false
////                    _isBtnLoginEnabled.value = false
////                    onLoginError()
////                }
//
//            }
//        }else{
//            _email.value = ""
//            _password.value = ""
//            _correctLogin.value = false
//            _isBtnLoginEnabled.value = false
//            onLoginError()
//        }
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String) = password.length >= 8


}