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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wodconnect.view.HomeScreen
import com.example.wodconnect.view.LoginScreen
import com.example.wodconnect.viewModel.LoginViewModel
import com.example.wodconnect.view.ReserveScreen
import com.example.wodconnect.view.ResetPasswordScreen
import com.example.wodconnect.ui.theme.WODConnectTheme
import com.example.wodconnect.view.AdminScreen
import com.example.wodconnect.view.AgendaScreen
import com.example.wodconnect.view.HorarioScreen
import com.example.wodconnect.view.OwnerScreen
import com.example.wodconnect.view.PerfilScreen
import com.example.wodconnect.viewModel.PerfilViewModel
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
                    reserveViewModel = viewModel
                )
            }
            composable("PerfilScreen"){
                val viewModel: PerfilViewModel = hiltViewModel()
                PerfilScreen(navController = navController,
                    perfilViewModel = viewModel
                )
            }
            composable(
                route = "PerfilScreen?modoRegistro={modoRegistro}",
                arguments = listOf(navArgument("modoRegistro") {
                    type = NavType.BoolType
                    defaultValue = false
                })
            ) { backStackEntry ->
                val modoRegistro = backStackEntry.arguments?.getBoolean("modoRegistro") ?: false
                PerfilScreen(navController = navController, modoRegistro = modoRegistro)
            }
            composable("HorarioScreen") {
               HorarioScreen(navController = navController)
            }
            composable("AgendaScreen") {
                AgendaScreen(navController = navController)
            }

            composable("OwnerScreen") {
                OwnerScreen(navController = navController)
            }

            composable( "AdminScreen") {
                AdminScreen(navController = navController)
            }

        }

    }
}

