package com.example.wodconnect.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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

@Composable
fun HomeScreen(navController: NavController,
               modifier: Modifier = Modifier) {

    Box(modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.dumbel),
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.8f
        )
        Home(
            modifier = Modifier.fillMaxSize(),
            navController = navController

        )
    }

}

@Composable
fun Home(modifier: Modifier, navController: NavController) {
Column(modifier = modifier.verticalScroll(rememberScrollState())
    .padding(16.dp),
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally)
{
    Spacer(modifier = Modifier.height(46.dp))

    Text(
        text = stringResource(R.string.home_text),
        fontFamily = FontFamily(Font(R.font.anton_regular)),
        modifier = Modifier
            .padding(top = 28.dp),

        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 50.sp,
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
    Spacer(modifier = Modifier.height(260.dp))

    Text(text = stringResource(R.string.app_name).uppercase(),
        fontFamily = FontFamily(Font(R.font.anton_regular)),
        fontSize = 62.sp,
        style = TextStyle(shadow = Shadow(color = Color.Black,
            offset = Offset(4f, 4f),
            blurRadius = 6f),
            letterSpacing = 2.sp),
        color = Color.White
        )

    Spacer(modifier = Modifier.height(64.dp))

    Button(
        onClick = {
            navController.navigate("login")
        },
        modifier = Modifier
            .width(250.dp)
            .height(68.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.btn_color),
            disabledContainerColor = colorResource(R.color.btn_disable_color),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )

    ) {
        Text(text = stringResource(R.string.home_btn),
            color = Color.White,
            fontSize = 28.sp)


    }
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