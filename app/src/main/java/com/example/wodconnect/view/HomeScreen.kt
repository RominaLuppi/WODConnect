package com.example.wodconnect.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wodconnect.R
import com.example.wodconnect.ui.theme.BackgroundButton
import com.example.wodconnect.ui.theme.ShapeButton

@Composable
fun HomeScreen(navController: NavController,
               modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()
    ) {
        Home(modifier = Modifier.fillMaxSize(),
            navController = navController)
    }
}

@Composable
fun Home(modifier: Modifier, navController: NavController) {
Column(modifier = modifier
    .verticalScroll(rememberScrollState())
    .fillMaxSize()
    .background(Brush.verticalGradient(listOf(Color.Gray, Color.Black),
        startY = 0f, endY = 1350f))
    .padding(16.dp),
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally)
{

    Image(
        painter = painterResource(R.drawable.dumbel),
        modifier = Modifier
            .padding(top = 54.dp)
            .clip(CircleShape)
            .size(200.dp),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.weight(1f))

    Text(
        text = stringResource(R.string.home_text),
        fontFamily = FontFamily(Font(R.font.anton_regular)),
        modifier = Modifier
            .padding(top = 28.dp),

        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        style = TextStyle(shadow = Shadow(
            color = Color.Black,
            offset = Offset(4f, 4f),
            blurRadius = 4f),
            letterSpacing = 3.sp
            ),
        lineHeight = 74.sp
    )
    Spacer(modifier = Modifier.weight(1f))

    Button(
        onClick = {
            navController.navigate("LoginScreen")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.btn_color),
            contentColor = Color.White,
        )
    ) {
        Text(text = stringResource(R.string.btn_registrarse),
            color = Color.White,
            fontSize = 20.sp)
    }
    Spacer(modifier = Modifier.height(8.dp))
    CustomButton(
        Modifier.clickable { },
        painterResource(id = R.drawable.google),
        stringResource(R.string.btn_google)
    )
    Spacer(modifier = Modifier.height(8.dp))
    CustomButton(
        Modifier.clickable { },
        painterResource(id = R.drawable.facebook),
        stringResource(R.string.btn_facebook)
    )
    Text(
        text = stringResource(R.string.btn_ya_registrado),
        color = Color.White,
        modifier = Modifier
            .padding(24.dp)
            .clickable { navController.navigate("LoginScreen") },
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.weight(1f))
}
}
//se define el estilo de los botones para reutilizar la funcion
@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(BackgroundButton)
            .border(2.dp, ShapeButton, CircleShape),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    val navController = rememberNavController()
    HomeScreen(
        navController = navController,

    )
}