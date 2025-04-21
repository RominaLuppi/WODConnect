package com.example.wodconnect.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wodconnect.R

@Composable
fun ResetPasswordScreen(navController: NavController, modifier: Modifier) {
    var email by remember { mutableStateOf("") }
    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_screen))
            .padding(24.dp)
            .focusable()
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(modifier = Modifier
                .width(200.dp)
                .padding(top = 28.dp)
                .clip(RoundedCornerShape(16.dp)),
                painter = painterResource(R.drawable.logo_app),
                contentDescription = "logo",
                contentScale = ContentScale.Fit)

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                stringResource(R.string.txt_reset_pasw),
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp)
                    .align(alignment = Alignment.Start),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
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
                    focusedContainerColor = colorResource(R.color.background_screen),
                    unfocusedContainerColor = colorResource(R.color.background_screen),
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { //hacer logica boton y comprobar campo email es correcto.

                },
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
                Text(text = stringResource(R.string.btn_reset))

            }
        }
    }

}

@Preview
@Composable
fun ResetPasswordScreenPreview() {
    val navController = rememberNavController()
    ResetPasswordScreen(
        navController = navController,
        modifier = Modifier

    )
}