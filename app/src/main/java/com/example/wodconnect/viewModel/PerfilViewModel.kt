package com.example.wodconnect.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wodconnect.modelo.repositories.PerfilRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val perfilRepository: PerfilRepository

) : ViewModel() {

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _fechaNac = MutableLiveData("")
    val fechaNac: LiveData<String> = _fechaNac

    private val _registroExitoso = MutableLiveData<Boolean?>()
    val registroExitoso: LiveData<Boolean?> = _registroExitoso

    val errorMsg = MutableLiveData<String?>()

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onFechaNacChanged(newFechaNac: String) {
        _fechaNac.value = newFechaNac
    }

    fun guardarPerfil() {
        val nombre = _name.value ?: ""
        val email = _email.value ?: ""
        val fechaNac = _fechaNac.value ?: ""

        if (!esFechaNacimValida(fechaNac)){
            errorMsg.value = "La fecha de nacimiento no es válida"
            return
        }
        viewModelScope.launch {
            val result = perfilRepository.guardarPerfil(nombre, email, fechaNac)
            if (result.isSuccess) {
                _registroExitoso.value = true
            } else {
                _registroExitoso.value = false
            }
        }
    }
//Crear y guardar un usuario nuevo
    fun registrarYGuardarPerfil(
        email: String,
        password: String,
        nombre: String,
        fechaNac: String
    ) {
    if (!esFechaNacimValida(fechaNac)){
        errorMsg.value = "La fecha de nacimiento no es válida"
        return
    }
        viewModelScope.launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val uid =
                    firebaseAuth.currentUser?.uid
                if (uid == null){
                    throw Exception("Usuario no identificado")
                }
                val datosPerfil = mapOf(
                    "nombre" to nombre,
                    "email" to email,
                    "fechaNacimiento" to fechaNac
                )
                firestore.collection("Usuarios").document(uid).set(datosPerfil).await()

                _registroExitoso.value = true
            } catch (e: Exception) {
                _registroExitoso.value = false
            }
        }
    }

    fun resetRegistroExitoso() {
        _registroExitoso.value = null
    }

    //cargar los datos del usuario en su perfil si ya esta registrado
    fun cargarPerfilUsuario(){
        val uid = firebaseAuth.currentUser?.uid ?: return

        viewModelScope.launch {
            try{
                val documento = firestore.collection("Usuarios")
                    .document(uid)
                    .get()
                    .await()
                documento?.let { _name.value = it.getString("nombre") ?: ""
                _email.value = it.getString("email") ?: ""
                _fechaNac.value = it.getString("fechaNacimiento") ?: ""
                }
            } catch (e: Exception){
                errorMsg.value = "Error al cargar el perfil ${e.message}"
            }
        }
    }
    //comprobación de fecha válida
    fun esFechaNacimValida(fecha: String): Boolean {
        return try {
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//            formato.isLenient = false
            val fechaIngresada = formato.parse(fecha)
            val fechaActual = Date()
            fechaIngresada.before(fechaActual)
        } catch (e: Exception) {
            false
        }
    }

}