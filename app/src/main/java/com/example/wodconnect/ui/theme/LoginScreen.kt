package com.example.wodconnect.ui.theme

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.wodconnect.R
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController


@Composable
fun LoginScreen(navController: NavController,
                viewModel: LoginViewModel,
                modifier: Modifier = Modifier) {
    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_screen))
            .padding(16.dp)
            .focusable()
    ) {
        Login(navController = navController, modifier = Modifier.fillMaxSize(), viewModel)
    }
}

@Composable
fun Login(navController: NavController, modifier: Modifier, viewModel: LoginViewModel) {
    val email : String by viewModel.email.observeAsState(initial = "") //se engancha la vista al LifeData del viewModel
    val password : String by viewModel.password.observeAsState(initial = "")
    val correctLogin : Boolean by viewModel.correctLogin.observeAsState(initial = true)

    Column(modifier = modifier.verticalScroll(rememberScrollState())) //para hacer scroll al rotar la pantalla
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

        Column(modifier = Modifier.fillMaxSize())
        {
            Text(text = stringResource(R.string.login),
                modifier = Modifier.padding(24.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,

                )

            EmailField(email) {viewModel.onLoginChanged(it, password)}

            Spacer(modifier = Modifier.height(24.dp))

            PasswordField(password) {viewModel.onLoginChanged(email, it)}

            Spacer(modifier = Modifier.height(24.dp))

            ForgotPassword(navController = navController, modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(24.dp))

            BtnLogin(correctLogin) {viewModel.onLoginSelected{
                navController.navigate("reserve")
            }}
            if (!correctLogin){
                Text(text = stringResource(R.string.msj_error),
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }

    }

}

@Composable
fun ForgotPassword(navController: NavController, modifier: Modifier) {
    Text(text = stringResource(R.string.forgot_password),
        modifier = modifier.clickable {
            navController.navigate("resetPassword")
        },
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        color = Color.Gray)
}

@Composable
fun BtnLogin(correctLogin: Boolean, onLoginSelected: () -> Unit) {
    Button(onClick = { onLoginSelected()},
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.btn_color),
            disabledContainerColor = colorResource(R.color.btn_disable_color),
            contentColor = Color.White,
            disabledContentColor = Color.White
            ),
        enabled = correctLogin
    ){
        Text(text = stringResource(R.string.btn_login))

    }

}

@Composable
fun EmailField(email : String, onTextFieldChanged:(String) -> Unit) {

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
        onValueChange = { onTextFieldChanged (it)},
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






