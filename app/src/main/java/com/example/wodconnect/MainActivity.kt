package com.example.wodconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wodconnect.view.HomeScreen
import com.example.wodconnect.view.LoginScreen
import com.example.wodconnect.viewModel.LoginViewModel
import com.example.wodconnect.view.ReserveScreen
import com.example.wodconnect.view.ResetPasswordScreen
import com.example.wodconnect.ui.theme.WODConnectTheme
import com.example.wodconnect.viewModel.ReserveViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WODConnectTheme {
                MainNavigation()
            }
        }
    }
}

@Composable
private fun MainNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = "HomeScreen"
        )
        {
            composable("HomeScreen")
            {
                HomeScreen(navController = navController)
            }
            composable("LoginScreen") {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    navController = navController,
                    loginViewModel = loginViewModel
                )
            }
            composable("ResetPasswordScreen") {
                ResetPasswordScreen(navController = navController, modifier = Modifier)
            }
            composable("ReserveScreen") {
                val viewModel: ReserveViewModel = hiltViewModel()
                ReserveScreen(
                    navController = navController,
                    modifier = Modifier,
                    reserveViewModel = viewModel
                )
            }
        }
    }
}

