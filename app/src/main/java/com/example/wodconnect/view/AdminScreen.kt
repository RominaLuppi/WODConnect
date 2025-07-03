package com.example.wodconnect.view


import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wodconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    userEmail: String,
    onLogout: () -> Unit
) {
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Text(
                        stringResource(R.string.title_admin_screen),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            )
        },

        ) { paddingScaffold ->
        Column(
            modifier = Modifier.padding(paddingScaffold)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(18.dp))
        }
        Text(
            text = stringResource(R.string.welcome_admin_screen),
            color = Color.White,
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(32.dp))

        AdminOptionButton("Gestión de Usuarios"){

        }

    }
}

@Composable
fun AdminOptionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.btn_color),
            disabledContainerColor = colorResource(R.color.btn_disable_color),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
    ){
        Text(
            text = text,
            fontSize = 20.sp
        )
    }

}
