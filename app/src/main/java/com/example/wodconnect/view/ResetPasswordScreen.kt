package com.example.wodconnect.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wodconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(navController: NavController, modifier: Modifier) {
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = stringResource(R.string.text_icon_btn),
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
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
        },

        ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .focusable()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 42.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = painterResource(R.drawable.logo_app),
                    contentDescription = "logo",
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(42.dp))

                Text(
                    stringResource(R.string.txt_reset_pasw),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(28.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    placeholder = { Text(stringResource(R.string.email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, Color.Gray, shape = MaterialTheme.shapes.small),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.btn_color),
                        disabledContainerColor = colorResource(R.color.btn_disable_color),
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.btn_reset),
                        fontSize = 20.sp
                    )
                }
                if (showDialog) {
                    BtnDialog(
                        onDismiss = { showDialog = false }
                    )
                }
            }
        }
    }
}
@Composable
fun BtnDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.title_dialog),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )
        },
        containerColor = Color.Black,
        tonalElevation = 24.dp,
        text = {
            Text(
                text = stringResource(id = R.string.msj_dialog),
                fontSize = 18.sp,
                color = Color.White
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(id = R.color.btn_color)
                )
            ) {
                Text(
                    text = stringResource(R.string.btn_dialog),
                    fontSize = 16.sp
                )
            }
        }
    )
}


