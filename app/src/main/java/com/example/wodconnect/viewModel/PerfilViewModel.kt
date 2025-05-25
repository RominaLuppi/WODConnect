package com.example.wodconnect.viewModel


import android.net.Uri
import android.util.Log
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
    private val firebaseAuth: FirebaseAuth,
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

    val photoUrl = MutableLiveData<String?>()
    val errorMsg = MutableLiveData<String?>()

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
    fun actualizarFoto(uri: Uri) {
        val uid = firebaseAuth.currentUser?.uid ?: return

        viewModelScope.launch {
            val result = perfilRepository.actualizarPerfilConFoto(uri, uid)
            if (result.isSuccess) {
               photoUrl.value = result.getOrNull().toString()
            } else {
                Log.e("PerfilViewModel", "Error subiendo foto", result.exceptionOrNull())
            }
        }
    }

    fun cargarFotoPerfil(){
        val userId = firebaseAuth.currentUser?.uid ?: return
        viewModelScope.launch {
            val result = perfilRepository.obtenerUrlFotoPerfil(userId)

            if (result.isSuccess){
                photoUrl.value = result.getOrNull()
            } else {
                errorMsg.value = "Error al cargar la foto: ${result.exceptionOrNull()?.message}"
            }
        }
    }

}