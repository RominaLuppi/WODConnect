package com.example.wodconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WODConnectTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    NavHost(navController = navController, startDestination = "home"){
                        composable("home") {
                            HomeScreen(navController = navController)
                        }
                        composable("login") {
                            val viewModel: LoginViewModel = viewModel()
                            LoginScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("resetPassword") {
                            ResetPasswordScreen(navController = navController, modifier = Modifier)
                        }
                        composable("reserve") {
                            val viewModel: ReserveViewModel = viewModel()
                            ReserveScreen(navController = navController, modifier = Modifier, viewModel = viewModel)
                        }
                    }

                }
            }
        }
    }
}
