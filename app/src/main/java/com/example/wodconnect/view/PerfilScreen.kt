package com.example.wodconnect.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wodconnect.viewModel.LoginViewModel

@Composable
fun PerfilScreen(navController: NavController,
                 modifier: Modifier,
                 loginViewModel: LoginViewModel) {

    Box(modifier = Modifier.fillMaxSize()
        .background(Color.Black))
    {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            Button(onClick = {
                //aqui se llama a la funcion regsiter
//                loginViewModel.register(email, password)
            }) {

            }
        }


        }
}
