package com.example.wodconnect.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wodconnect.R
import com.example.wodconnect.viewModel.LoginViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel
) {
    val loginError by loginViewModel.loginError.observeAsState()
    val isLoading by loginViewModel.isLoading.observeAsState(false)
    val snackbarHostState = remember { SnackbarHostState()}

    LaunchedEffect(loginError) {
        loginError?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_screen))
            .padding(16.dp)
            .focusable()
    ) {
        Login(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            loginViewModel = loginViewModel
        )
        //para mostrar errores
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        if (isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

@Composable
fun Login(
    navController: NavController,
    modifier: Modifier,
    loginViewModel: LoginViewModel
) {
    val email: String by loginViewModel.email.observeAsState(initial = "") //se engancha la vista al LiveData del viewModel
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val correctLogin: Boolean by loginViewModel.correctLogin.observeAsState(initial = true)
    val loginError: String? by loginViewModel.loginError.observeAsState()
    val isBtnLoginEnabled by loginViewModel.isBtnLoginEnabled.observeAsState(initial = false)


    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) //para hacer scroll al rotar la pantalla
    {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp)
                .clip(RoundedCornerShape(16.dp)),
            painter = painterResource(R.drawable.logo_app),
            contentDescription = "logo",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        {
            Text(
                text = stringResource(R.string.login),
                modifier = Modifier
                    .padding(24.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,

                )

            EmailField(email) { loginViewModel.onLoginChanged(it, password) }

            Spacer(modifier = Modifier.height(24.dp))

            PasswordField(password) { loginViewModel.onLoginChanged(email, it) }

            Spacer(modifier = Modifier.height(24.dp))

            ForgotPassword(
                navController = navController,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            BtnLogin(enabled = isBtnLoginEnabled) {
                loginViewModel.onLoginSelected(
                    onLoginSucess = {
                        navController.navigate("ReserveScreen")
                    },
                    onLoginError = { errorMessage ->
                        loginViewModel._loginError.value = errorMessage
                    }
                )
            }
        }
    }

}

@Composable
fun ForgotPassword(navController: NavController, modifier: Modifier) {
    Text(
        text = stringResource(R.string.forgot_password),
        modifier = modifier.clickable {
            navController.navigate("resetPassword")
        },
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        color = Color.Gray
    )
}

@Composable
fun BtnLogin(
    enabled: Boolean,
    onLoginSelected: () -> Unit
) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.btn_color),
            disabledContainerColor = colorResource(R.color.btn_disable_color),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = enabled
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
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
        placeholder = { Text(stringResource(R.string.email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = stringResource(R.string.email)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.background_screen),
            unfocusedContainerColor = colorResource(R.color.background_screen),
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
            .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
        placeholder = { Text(stringResource(R.string.password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = stringResource(R.string.password)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.background_screen),
            unfocusedContainerColor = colorResource(R.color.background_screen),
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray
        )
    )
}






