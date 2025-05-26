package com.example.wodconnect.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wodconnect.R
import com.example.wodconnect.viewModel.PerfilViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    perfilViewModel: PerfilViewModel = hiltViewModel(),
    modoRegistro: Boolean = false
) {
    val name by perfilViewModel.name.observeAsState()
    val email by perfilViewModel.email.observeAsState()
    val password by perfilViewModel.password.observeAsState()
    val fechaNac by perfilViewModel.fechaNac.observeAsState()
    val registroExitoso by perfilViewModel.registroExitoso.observeAsState()


    Scaffold(containerColor = Color.Black,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        Text(text = stringResource(R.string.text_icon_btn),
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { navController.popBackStack() })
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("LoginScreen")
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.text_icon_btn),
                            tint = Color.White
                        )
                    }
                }
            )
        }) { paddingScaffold ->

        Column(
            modifier = Modifier
                .padding(paddingScaffold)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        )
        {
            Text(
                text = stringResource(R.string.icon_perfil),
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://as1.ftcdn.net/v2/jpg/04/43/35/22/1000_F_443352225_82bID6iDPlFpQwUFMLEziZuBXhrrh48T.jpg",
                    placeholder = painterResource(R.drawable.icono_perfil_usuario),
                    error = painterResource(R.drawable.icono_perfil_usuario),
                    contentDescription = stringResource(R.string.text_foto_perfil),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    alignment = Alignment.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Perfil(name = name ?: "",
                email = email ?: "",
                password = password ?: "",
                fechaNac = fechaNac ?: "",
                onNameChanged = { perfilViewModel.onNameChanged(it) },
                onEmailChanged = { perfilViewModel.onEmailChanged(it) },
                onPasswordChanged = { perfilViewModel.onPasswordChanged(it) },
                onFechaNacChanged = { perfilViewModel.onFechaNacChanged(it) })

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (modoRegistro){
                        perfilViewModel.registrarYGuardarPerfil(
                            email = email ?: "",
                            password = password ?: "",
                            nombre = name ?: "",
                            fechaNac = fechaNac ?: ""
                        )
                    } else {
                        perfilViewModel.guardarPerfil()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 32.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.btn_color),
                    disabledContainerColor = colorResource(R.color.btn_disable_color),
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
            )
            {
                Text(
                    text = stringResource(R.string.btn_guardar),
                    fontSize = 20.sp
                )
            }
        }
    }
    LaunchedEffect(registroExitoso) {
        Log.d("PerfilScreen", "registroExitoso cambiado a $registroExitoso")
        if (registroExitoso == true) {
            navController.navigate("LoginScreen") {
                popUpTo("PerfilScreen") { inclusive = true }
            }
            perfilViewModel.resetRegistroExitoso()
        }
    }
    LaunchedEffect(Unit) {
        if (!modoRegistro){
            perfilViewModel.cargarPerfilUsuario()
        }
    }
}

@Composable
fun Perfil(
    name: String,
    email: String,
    password: String,
    fechaNac: String,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onFechaNacChanged: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChanged,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
                placeholder = { Text(stringResource(R.string.text_nombre_usuario)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                colors = textViewFieldColor()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChanged,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
                placeholder = { Text(stringResource(R.string.text_email_usuario)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = textViewFieldColor()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChanged,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
                placeholder = { Text(stringResource(R.string.text_password_usuario)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = textViewFieldColor()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = fechaNac,
                onValueChange = onFechaNacChanged,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
                placeholder = { Text(stringResource(R.string.text_fechaNac_usuario)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                colors = textViewFieldColor()
            )
        }
    }
}

@Composable
fun textViewFieldColor() = TextFieldDefaults.colors(
    focusedContainerColor = Color.LightGray,
    unfocusedContainerColor = Color.LightGray,
    focusedLabelColor = Color.Gray,
    unfocusedLabelColor = Color.Gray
)


