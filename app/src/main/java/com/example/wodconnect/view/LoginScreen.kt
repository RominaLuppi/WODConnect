package com.example.wodconnect.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wodconnect.R
import com.example.wodconnect.viewModel.LoginViewModel
import kotlinx.coroutines.delay


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val user by loginViewModel.user.observeAsState()
    val isLoading by loginViewModel.isLoading.observeAsState(false)
    val errorMessage by loginViewModel.errorMessage.observeAsState()

    val email by loginViewModel.email.observeAsState("")
    val password by loginViewModel.password.observeAsState("")


    //si el User no es null se navega a la pantalla HomeScreen
    LaunchedEffect(user) {
        user?.let {
            navController.navigate("ReserveScreen") {
                popUpTo("LoginScreen") {
                    inclusive = true
                } //elimina la pantalla de login del backstack.
            }
        }
    }
    Login(
        navController = navController,
        loginViewModel = loginViewModel,
        email = email,
        password = password
    )

    LaunchedEffect(errorMessage) {
        errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            if (error == "Usuario no registrado. Por favor, regístrate") {
                delay(2000) // Espera para que el Toast se vea
                navController.navigate("PerfilScreen?modoRegistro=true")
                loginViewModel.cleanError()
            } else {
                loginViewModel.cleanError()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavController,
    loginViewModel: LoginViewModel,
    email: String,
    password: String
) {
    val errorMessage by loginViewModel.errorMessage.observeAsState()
    val registroExitoso by loginViewModel.registroExitoso.observeAsState()

    Scaffold( containerColor = Color.Black,
          topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = stringResource(R.string.text_icon_btn),
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                            navController.navigate("HomeScreen")
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

        Column(modifier = Modifier.padding(paddingScaffold)
            .verticalScroll(rememberScrollState()))
        {
            Spacer(modifier = Modifier.height(18.dp))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .heightIn(max = 300.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = painterResource(R.drawable.logo_wodconnect),
                contentDescription = stringResource(R.string.text_logo),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.login),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,

                )
            Spacer(modifier = Modifier.height(24.dp))
            EmailField(email = email,
                onTextFieldChanged = { loginViewModel.onEmailChanged(it) })

            Spacer(modifier = Modifier.height(16.dp))
            PasswordField(password = password,
                onTextFieldChanged = { loginViewModel.onPasswordChanged(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            ForgotPassword(
                navController = navController,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))
            BtnLogin(
                loginViewModel = loginViewModel,
                email = email,
                password = password
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = stringResource(R.string.btn_crear_cuenta),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { navController.navigate("PerfilScreen?modoRegistro=true") }
            )
        }

    }

}

@Composable
fun ForgotPassword(navController: NavController, modifier: Modifier) {
    Text(
        text = stringResource(R.string.forgot_password),
        modifier = modifier.clickable {
            navController.navigate("ResetPasswordScreen")
        },
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        color = Color.Gray
    )
}

@Composable
fun BtnLogin(
    loginViewModel: LoginViewModel,
    email: String,
    password: String
) {
    Button(
        onClick = { loginViewModel.login(email, password) },
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

        ) {
        Text(
            text = stringResource(R.string.btn_login),
            fontSize = 20.sp
        )

    }

}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {

    OutlinedTextField(
        value = email,
        onValueChange = onTextFieldChanged,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
            .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
        placeholder = { Text(stringResource(R.string.email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = stringResource(R.string.email)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray
        )
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {

    OutlinedTextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
        placeholder = { Text(stringResource(R.string.password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = stringResource(R.string.password)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray
        )
    )
}






