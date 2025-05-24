package com.example.wodconnect.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wodconnect.modelo.repositories.PerfilRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val perfilRepository: PerfilRepository

) : ViewModel(){

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _fechaNac = MutableLiveData("")
    val fechaNac: LiveData<String> = _fechaNac

    private val _resultado = MutableLiveData<Result<Unit>>()
    val resultado: LiveData<Result<Unit>> = _resultado

    fun onNameChanged(newName: String){
        _name.value = newName
    }
    fun onEmailChanged(newEmail: String){
        _email.value = newEmail
    }
    fun onFechaNacChanged(newFechaNac: String){
        _fechaNac.value = newFechaNac
    }

    fun guardarPerfil(
        nombre: String = _name.value ?: "",
        email: String = _email.value ?: "",
        fechaNac: String = _fechaNac.value ?: ""
    ){
        viewModelScope.launch {
            val result = perfilRepository.guardarPerfil(nombre, email, fechaNac)
            _resultado.value = result
        }


    }
}